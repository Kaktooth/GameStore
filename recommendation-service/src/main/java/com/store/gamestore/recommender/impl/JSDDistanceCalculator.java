package com.store.gamestore.recommender.impl;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.lit;

import com.store.gamestore.common.ApplicationConstants.Columns;
import com.store.gamestore.common.ApplicationConstants.Symbols;
import com.store.gamestore.common.ApplicationConstants.UDF;
import com.store.gamestore.persistence.entity.GameRecommendation;
import com.store.gamestore.persistence.repository.GameRecommendationRepository;
import com.store.gamestore.persistence.repository.GameTitleMetadataRepository;
import com.store.gamestore.recommender.SimilarityCalculator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JSDDistanceCalculator implements SimilarityCalculator {

  private final GameRecommendationRepository gameRecommendationRepository;
  private final GameTitleMetadataRepository gameMetadataRepository;

  @Override
  public Dataset<Row> calculateSimilarities(Dataset<Row> dataset) {
    log.info("Calculate similarities for all pairs in dataset");
    var crossJoinedDataset = crossJoin(dataset);
    var calculatedJSDDataset = calculateJSD(crossJoinedDataset);
    calculatedJSDDataset.show(false);
    saveGamesSimilarities(calculatedJSDDataset);
    return calculatedJSDDataset;
  }

  private Dataset<Row> crossJoin(Dataset<Row> dataset) {
    return dataset
        .crossJoin(dataset.withColumnRenamed(Columns.TITLE_COLUMN, Columns.SECOND_TITLE_COLUMN)
            .withColumnRenamed(Columns.TOPIC_DISTRIBUTION, Columns.SECOND_TOPIC_DISTRIBUTION))
        .filter(col(Columns.TITLE_COLUMN).notEqual(col(Columns.SECOND_TITLE_COLUMN)));
  }

  private Dataset<Row> calculateJSD(Dataset<Row> crossJoinedDataset) {
    return crossJoinedDataset
        .select(concat(col(Columns.TITLE_COLUMN), lit(Symbols.BAR),
                col(Columns.SECOND_TITLE_COLUMN)).as(Columns.PAIR),
            col(Columns.SECOND_TITLE_COLUMN), col(Columns.TOPIC_DISTRIBUTION),
            col(Columns.SECOND_TOPIC_DISTRIBUTION))
        .withColumn(Columns.JENSEN_SHANNON_DISTANCE, callUDF(UDF.CALCULATE_JENSEN_SHANNON_DISTANCE,
            col(Columns.TOPIC_DISTRIBUTION), col(Columns.SECOND_TOPIC_DISTRIBUTION)));
  }

  private void saveGamesSimilarities(Dataset<Row> calculatedSimilarities) {
    var pairSimilarities = getSimilarityPairs(calculatedSimilarities);
    for (var pairSimilarity : pairSimilarities) {
      var titlePair = pairSimilarity.getLeft().split(Symbols.Patterns.BAR);
      var similarity = pairSimilarity.getRight();
      var firstGame = gameMetadataRepository.findByTitle(titlePair[0]);
      var secondGame = gameMetadataRepository.findByTitle(titlePair[1]);
      var gamesSimilarity = GameRecommendation.builder()
          .id(UUID.randomUUID())
          .firstGameId(firstGame.getId())
          .secondGameId(secondGame.getId())
          .similarity(similarity)
          .build();
      if (!gameRecommendationRepository.existsByFirstGameIdAndSecondGameIdAndSimilarity(
          gamesSimilarity.getFirstGameId(), gamesSimilarity.getSecondGameId(), similarity)) {
        gameRecommendationRepository.save(gamesSimilarity);
      }
    }
    log.info("games similarity saved");
  }

  private List<ImmutablePair<String, Double>> getSimilarityPairs(Dataset<Row> similarities) {
    return similarities.toJavaRDD()
        .map(row -> {
          var titlePair = row.<String>getAs(Columns.PAIR);
          var similarity = row.<Double>getAs(Columns.JENSEN_SHANNON_DISTANCE);
          return new ImmutablePair<>(titlePair, similarity);
        }).collect();
  }
}
