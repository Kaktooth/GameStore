package com.store.gamestore.recommender.impl;

import static org.apache.spark.sql.functions.col;

import com.store.gamestore.common.ApplicationConstants.Columns;
import com.store.gamestore.common.ApplicationConstants.FileFormats;
import com.store.gamestore.common.ApplicationConstants.LDAConstants;
import com.store.gamestore.common.ApplicationConstants.LoadingPaths;
import com.store.gamestore.common.ApplicationConstants.RecommenderConstants;
import com.store.gamestore.common.ApplicationConstants.UDF;
import com.store.gamestore.recommender.DataPreProcessor;
import com.store.gamestore.recommender.FeaturesExtractor;
import com.store.gamestore.recommender.TopicFinder;
import com.store.gamestore.recommender.TrainedModel;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.ml.Model;
import org.apache.spark.ml.clustering.LDA;
import org.apache.spark.ml.clustering.LDAModel;
import org.apache.spark.ml.clustering.LocalLDAModel;
import org.apache.spark.ml.feature.CountVectorizerModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LDATrainedModel implements TrainedModel<LDAModel>, TopicFinder {

  private final SparkSession sparkSession;
  private final DataPreProcessor dataPreProcessor;
  private final FeaturesExtractor<CountVectorizerModel> featuresExtractor;

  @Override
  public Set<String> findPopularTopicTerms(Dataset<Row> topicsDataset, String[] vocabulary,
      Integer topic) {

    var termIndices = topicsDataset.filter(col(Columns.TOPIC_COLUMN).equalTo(topic))
        .first().<Integer>getList(1);

    return termIndices.stream()
        .sorted()
        .limit(RecommenderConstants.TOPIC_NUMBER)
        .map(termIndex -> vocabulary[termIndex])
        .collect(Collectors.toSet());
  }

  @Override
  public Model<LDAModel> getTrainedModel() {
    log.info("load trained model...");

    //TODO this code only show data and for that method is ambiguous
//    Dataset<Row> describedTopics = sparkSession.read()
//        .schema(DatasetSchemes.DESCRIBED_TOPICS_SCHEMA)
//        .parquet(LoadingPaths.LDA_DESCRIBED_TOPICS);
//
//    Dataset<Row> vocabulary = sparkSession.read()
//        .schema(DatasetSchemes.VOCABULARY_SCHEMA)
//        .parquet(LoadingPaths.VOCABULARY);
//
//    describedTopics.show(false);
//    vocabulary.show(false);
//    showTopics(describedTopics);
    return LocalLDAModel.read()
        .session(sparkSession)
        .load(LoadingPaths.LDA_MODEL);
  }

  @Override
  public void trainModel() {
    var processedData = dataPreProcessor.processTextData(loadGameMetadata());
    var extractedFeatures = featuresExtractor.extractFeatures(processedData);

    var ldaModel = fitLDAModel(extractedFeatures);
    saveLDAModelData(ldaModel);
    saveDescribedLDATopics(ldaModel);
  }

  private Dataset<Row> loadGameMetadata() {
    var dataset = sparkSession.read().text(LoadingPaths.GAMES_METADATA_TEXT_FILE)
        .withColumn(Columns.TITLE_COLUMN,
            functions.callUDF(UDF.GENERATE_RANDOM_STRING, functions.lit(10)))
        .withColumnRenamed(Columns.VALUE_COLUMN, Columns.DESCRIPTION_COLUMN);
    dataset.show(false);
    return dataset;
  }

  private LDAModel fitLDAModel(Dataset<Row> extractedFeatures) {
    log.info("start LDA training...");
    LDA lda = new LDA()
        .setK(LDAConstants.TOPICS_NUMBER)
        .setMaxIter(LDAConstants.MAX_ITERATIONS);
    return lda.fit(extractedFeatures);
  }

  private void saveLDAModelData(LDAModel ldaModel) {
    try {
      log.info("save model data...");
      ldaModel.write().overwrite().save(LoadingPaths.LDA_MODEL);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void saveDescribedLDATopics(LDAModel ldaModel) {
    ldaModel.describeTopics().write().mode(SaveMode.Overwrite).format(FileFormats.PARQUET)
        .save(LoadingPaths.LDA_DESCRIBED_TOPICS);
  }

//  private void showTopics(Dataset<Row> topicsDataset) {
//    log.info("Number of topics : {}", topicsDataset.count());
//    var vocabulary = featuresExtractor.getExtractorModule().vocabulary();
//    topicsDataset.select(Columns.TOPIC_COLUMN, Columns.TERM_INDICES_COLUMN,
//            Columns.TERM_WEIGHTS_COLUMN)
//        .foreach((ForeachFunction<Row>) row -> {
//          var topic = row.<Integer>getAs(Columns.TOPIC_COLUMN);
//          var termIndices = row.<Integer>getList(1);
//          var termWeights = row.<Double>getList(2);
//
//          for (int i = 0; i < termIndices.size(); i++) {
//            var termIndex = termIndices.get(i);
//            var termWeight = termWeights.get(i);
//            var term = vocabulary[termIndices.get(i)];
//            log.info("topic: {} term: {} {} weight: {}", topic, termIndex, term, termWeight);
//          }
//        });
//  }
}
