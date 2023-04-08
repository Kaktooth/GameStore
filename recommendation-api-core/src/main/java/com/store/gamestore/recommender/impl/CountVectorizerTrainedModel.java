package com.store.gamestore.recommender.impl;

import com.store.gamestore.common.ApplicationConstants.Columns;
import com.store.gamestore.common.ApplicationConstants.FileFormats;
import com.store.gamestore.common.ApplicationConstants.LoadingPaths;
import com.store.gamestore.persistence.entity.GameMetadata;
import com.store.gamestore.persistence.repository.GameMetadataRepository;
import com.store.gamestore.recommender.DataPreProcessor;
import com.store.gamestore.recommender.TrainedModel;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.spark.ml.Model;
import org.apache.spark.ml.feature.CountVectorizer;
import org.apache.spark.ml.feature.CountVectorizerModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountVectorizerTrainedModel implements TrainedModel<CountVectorizerModel> {

  private final SparkSession sparkSession;
  private final DataPreProcessor dataPreProcessor;
  private final GameMetadataRepository gameMetadataRepository;

  @Override
  public Model<CountVectorizerModel> getTrainedModel() {
    return CountVectorizerModel.load(LoadingPaths.COUNT_VECTORIZER);
  }

  @Override
  public void trainModel() {

    Dataset<GameMetadata> gameMetadata = sparkSession.createDataset(
        gameMetadataRepository.findAll(),
        Encoders.bean(GameMetadata.class));

    var processedData = dataPreProcessor.processTextData(gameMetadata);

    var countVectorizer = new CountVectorizer()
        .setInputCol(Columns.FILTERED_TOKENS_COLUMN)
        .setOutputCol(Columns.FEATURES_COLUMN)
        .setMinDF(2)
        .fit(processedData);

    saveCountVectorizerModel(countVectorizer);
    saveVocabulary(countVectorizer);
  }

  private void saveCountVectorizerModel(CountVectorizerModel countVectorizerModel) {
    try {
      countVectorizerModel
          .write()
          .overwrite()
          .save(LoadingPaths.COUNT_VECTORIZER);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void saveVocabulary(CountVectorizerModel countVectorizerModel) {
    var vocabulary = countVectorizerModel.vocabulary();
    sparkSession.createDataset(List.of(vocabulary), Encoders.STRING())
        .withColumnRenamed(Columns.VALUE_COLUMN, Columns.WORD_COLUMN)
        .withColumn(Columns.INDEX_COLUMN, functions.monotonically_increasing_id())
        .write()
        .mode(SaveMode.Overwrite)
        .format(FileFormats.PARQUET)
        .save(LoadingPaths.VOCABULARY);
  }
}
