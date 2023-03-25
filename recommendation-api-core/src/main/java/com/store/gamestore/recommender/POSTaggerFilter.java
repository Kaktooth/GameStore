package com.store.gamestore.recommender;

import com.store.gamestore.common.ApplicationConstants.Columns;
import com.store.gamestore.common.ApplicationConstants.DatasetSchemes;
import com.store.gamestore.common.ApplicationConstants.LoadingPaths;
import com.store.gamestore.common.ApplicationConstants.WordClasses;
import com.store.gamestore.common.ConsolePrinter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.springframework.stereotype.Component;
import scala.collection.mutable.ArraySeq;

@Slf4j
@Component
@RequiredArgsConstructor
public class POSTaggerFilter implements DatasetFilter {

  private final SparkSession sparkSession;

  @Override
  public Dataset<Row> filterDataset(Dataset<Row> inputDataset) {
    var posTaggedWords = createPOSDataset(inputDataset);
    return filterNounWords(posTaggedWords);
  }

  private POSTaggerME loadPOSTagger() {
    try {
      log.info("Retrieving model from Internet...");
      return new POSTaggerME("en");
    } catch (IOException e) {
      log.error("Error to retrieve model from Internet: ", e);
      return new POSTaggerME(Objects.requireNonNull(loadModel()));
    }
  }

  private POSModel loadModel() {
    try {
      log.info("Retrieving model from memory...");
      var modelFile = new File(LoadingPaths.POS_TAGGER_MODEL);
      return new POSModel(modelFile);
    } catch (IOException e) {
      log.error("Failed loading model from memory: ", e);
      return null;
    }
  }

  private Dataset<Row> createPOSDataset(Dataset<Row> inputDataset) {
    var gamePairData = getWordsPairs(inputDataset);
    var taggedWords = taggingWords(gamePairData);
    var posTaggedTokens = sparkSession.createDataFrame(taggedWords,
        DatasetSchemes.TAGGED_TOKENS_SCHEMA);
    posTaggedTokens.show(false);
    return posTaggedTokens;
  }

  private List<Row> taggingWords(List<Pair<String, ArraySeq<String>>> wordsPairs) {
    var posTagger = loadPOSTagger();
    log.info("tagging words...");
    Callable<List<Row>> tagTask = () -> {
      var tags = new ArrayList<Row>();
      int counter = 0;
      for (var gameDataPair : wordsPairs) {
        var gameTokens = (String[]) gameDataPair.getRight().array();
        var posTags = posTagger.tag(gameTokens);
        tags.add(RowFactory.create(gameDataPair.getLeft(), gameTokens, posTags));
        counter++;
        ConsolePrinter.printProgress(wordsPairs.size(), counter);
      }
      return tags;
    };

    var executor = Executors.newWorkStealingPool(4);
    final var val = executor.submit(tagTask);
    try {
      return val.get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Pair<String, ArraySeq<String>>> getWordsPairs(Dataset<Row> tokensDataset) {
    log.info("collecting word pairs");
    return tokensDataset.toJavaRDD()
        .map(row -> {
          var gameTitle = row.<String>getAs(Columns.TITLE_COLUMN);
          var wordsArray = row.<ArraySeq<String>>getAs(Columns.FILTERED_TOKENS_COLUMN);
          return (Pair<String, ArraySeq<String>>) new ImmutablePair<>(gameTitle, wordsArray);
        })
        .collect();
  }

  private Dataset<Row> filterNounWords(Dataset<Row> words) {
    log.info("finding noun words...");
    ExpressionEncoder<Row> filteredWordsEncoder = RowEncoder
        .apply(DatasetSchemes.FILTERED_TOKENS_SCHEMA);
    words = words.map((MapFunction<Row, Row>) row -> {
      var gameTitle = row.<String>getAs(Columns.TITLE_COLUMN);
      var posTagsArray = (String[]) row.<ArraySeq<String>>getAs(Columns.POS_TAGS_COLUMN).array();
      var tokensArray = (String[]) row.<ArraySeq<String>>getAs(Columns.FILTERED_TOKENS_COLUMN).array();
      var filteredTokens = new ArrayList<>();
      for (int i = 0; i < posTagsArray.length; i++) {
        if (posTagsArray[i].equals(WordClasses.NOUN)) {
          filteredTokens.add(tokensArray[i]);
        }
      }
      return RowFactory.create(gameTitle, filteredTokens.toArray());
    }, filteredWordsEncoder);
    words.show(false);

    return words;
  }
}
