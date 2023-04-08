package com.store.gamestore.recommender.impl;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

import com.store.gamestore.common.ApplicationConstants.Columns;
import com.store.gamestore.common.ApplicationConstants.UDF;
import com.store.gamestore.persistence.entity.GameRating;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.repository.GameRatingRepository;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import com.store.gamestore.recommender.RatingsEvaluator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;
import scala.Tuple2;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImplicitRatingsEvaluator implements RatingsEvaluator {

  private final SparkSession sparkSession;
  private final GameRatingRepository gameRatingRepository;
  private final UserInteractionRepository userInteractionRepository;

  @Override
  public List<GameRating> getRatedGames(UUID userId) {
    return gameRatingRepository.findAllByUserId(userId);
  }

  @Override
  public void evaluateRatings(UUID userId) {
    var userInteractions = userInteractionRepository.findAllByUserId(userId);
    log.info("evaluate ratings for user: {}", userId);
    var gameIds = userInteractions.stream().map(UserInteraction::getGameId)
        .collect(Collectors.toSet());

    List<Tuple2<String, Double>> gameRatings = new ArrayList<>();
    var maxWeight = 0.0d;
    var minWeight = 0.0d;

    for (var gameId : gameIds) {
      var overallWeight = 0.0d;
      for (var interaction : InteractionType.values()) {

        var interactions = userInteractionRepository.findAllByUserIdAndGameIdAndInteractionType(
            userId, gameId, interaction);
        final var haveIgnored = interaction == InteractionType.IGNORED && !interactions.isEmpty();

        var maxInteractions = userInteractionRepository.countMaxUserInteractions(userId,
            interaction);
        if (maxInteractions != null) {
          overallWeight += Math.min(interactions.size(), maxInteractions) * interaction.getWeight();
          if (haveIgnored) {
            overallWeight -= overallWeight;
          }
        }
      }
      maxWeight = Math.max(maxWeight, overallWeight);
      minWeight = Math.min(minWeight, overallWeight);
      gameRatings.add(new Tuple2<>(String.valueOf(gameId), overallWeight));
    }

    var userInteractionsDataset = sparkSession.createDataset(gameRatings,
            Encoders.tuple(Encoders.STRING(), Encoders.DOUBLE()))
        .withColumnRenamed(Columns.FIRST_COLUMN, Columns.ID_COLUMN)
        .withColumnRenamed(Columns.SECOND_COLUMN, Columns.VALUE_COLUMN)
        .withColumn(Columns.NORMILIZED_VALUE, callUDF(UDF.NORMILIZE,
            col(Columns.VALUE_COLUMN), functions.lit(maxWeight), functions.lit(minWeight)));
    userInteractionsDataset.show(false);

    var ratingList = userInteractionsDataset.map((MapFunction<Row, GameRating>) row -> {
          var gameId = row.<String>getAs(Columns.ID_COLUMN);
          var normalizedValue = row.<Double>getAs(Columns.NORMILIZED_VALUE);
          return new GameRating(UUID.randomUUID(), userId, UUID.fromString(gameId), normalizedValue,
              LocalDateTime.now());
        }, Encoders.javaSerialization(GameRating.class))
        .collectAsList();

    for (var rating : ratingList) {
      final var ratingExists = gameRatingRepository.existsByGameIdAndUserIdAndRating(
          rating.getGameId(), userId,
          rating.getRating());
      if (Boolean.FALSE.equals(ratingExists)) {
        gameRatingRepository.save(rating);
      }
    }
  }
}
