package com.store.gamestore.recommender.impl;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

import com.store.gamestore.common.ApplicationConstants.Columns;
import com.store.gamestore.persistence.entity.GameMetadata;
import com.store.gamestore.persistence.entity.GameRating;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.entity.UserRecommendation;
import com.store.gamestore.persistence.repository.GameMetadataRepository;
import com.store.gamestore.persistence.repository.GameRatingRepository;
import com.store.gamestore.persistence.repository.GameRecommendationRepository;
import com.store.gamestore.persistence.repository.GameTitleMetadataRepository;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import com.store.gamestore.persistence.repository.UserRecommendationRepository;
import com.store.gamestore.persistence.repository.UserRepository;
import com.store.gamestore.recommender.DataPreProcessor;
import com.store.gamestore.recommender.FeaturesExtractor;
import com.store.gamestore.recommender.Recommender;
import com.store.gamestore.recommender.TrainedModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.ml.clustering.LDAModel;
import org.apache.spark.ml.clustering.LocalLDAModel;
import org.apache.spark.ml.feature.CountVectorizerModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LDARecommender implements Recommender {

  private final UserRecommendationRepository userRecommendationRepository;
  private final GameRatingRepository gameRatingRepository;
  private final SparkSession sparkSession;
  private final UserRepository userRepository;
  private final DataPreProcessor dataPreProcessor;
  private final TrainedModel<LDAModel> ldaTrainedModel;
  private final GameMetadataRepository gameMetadataRepository;
  private final GameTitleMetadataRepository gameTitleMetadataRepository;
  private final GameRecommendationRepository gameRecommendationRepository;
  private final FeaturesExtractor<CountVectorizerModel> featuresExtractor;
  private final ImplicitRatingsEvaluator implicitRatingsEvaluator;
  private final JSDDistanceCalculator jsdDistanceCalculator;
  private final UserInteractionRepository userInteractionRepository;

  @Override
  public void recommend() {
    log.info("Start recommending...");

    log.info("Start evaluating game ratings for users...");
    var users = userRepository.findAll();
    users.forEach(user -> implicitRatingsEvaluator.evaluateRatings(user.getId()));

    log.info("Get game metadata...");
    Dataset<GameMetadata> gameMetadata = sparkSession.createDataset(
        gameMetadataRepository.findAll(),
        Encoders.bean(GameMetadata.class));

    var processedData = dataPreProcessor.processTextData(gameMetadata);
    var extractedFeatures = featuresExtractor.extractFeatures(processedData);

    var ldaModel = (LocalLDAModel) ldaTrainedModel.getTrainedModel();
    var describedTopics = ldaModel.describeTopics();
    describedTopics.show(false);

    double ll = ldaModel.logLikelihood(extractedFeatures);
    double lp = ldaModel.logPerplexity(extractedFeatures);
    log.info("The lower bound on the log likelihood of the entire corpus: " + ll);
    log.info("The upper bound on perplexity: " + lp);

    var distributedTopics = ldaModel.transform(extractedFeatures);
    log.info("Transformed data:");
    distributedTopics.show(false);

    var calculatedTopics = jsdDistanceCalculator.calculateSimilarities(distributedTopics);

    for (var user : users) {
      var ratedGames = gameRatingRepository.findAllByUserId(user.getId()).stream()
          .filter(gameRating -> gameRating.getRating() != 0)
          .collect(Collectors.toList());
      var gameIds = ratedGames.stream()
          .map(GameRating::getGameId)
          .collect(Collectors.toList());
      var games = gameTitleMetadataRepository.findAllById(gameIds);
      for (var game : games) {
        var mostUsedTopic = getMostUsedTopic(calculatedTopics, game.getTitle());
        saveRecommendation(game.getId(), user.getId(), mostUsedTopic);
      }
    }
  }

  public void saveRecommendation(UUID gameId, UUID userId, Integer topicId) {

    var currentDate = LocalDateTime.now();
    var recommendations = gameRecommendationRepository.findAllByFirstGameId(gameId);
    var userRecommendations = new ArrayList<UserRecommendation>();
    for (var recommendation : recommendations) {
      //TODO check. Can be multiple
      var userRecommendation = userRecommendationRepository.findFirstByUserIdAndGameIdAndTopicId(userId,
          recommendation.getSecondGameId(), topicId);
      if (userRecommendation == null) {
        userRecommendation = new UserRecommendation(UUID.randomUUID(),
            recommendation.getSimilarity(), currentDate, userId,
            recommendation.getSecondGameId(), topicId, getClass().getSimpleName());
        userRecommendations.add(userRecommendation);
      }

      if (!userRecommendation.getPredictedRating().equals(recommendation.getSimilarity())) {
        userRecommendation.setPredictedRating(recommendation.getSimilarity());
        userRecommendation.setLastRecommendedDate(currentDate);
        userRecommendations.add(userRecommendation);
      }
    }

    userRecommendationRepository.saveAll(userRecommendations);

    //TODO optimize
    //Remove ignored games from user recommendations
    final var ignoredGameIds = userInteractionRepository.findAllUserInteractions(userId,
            InteractionType.IGNORED, false).stream()
        .map(UserInteraction::getGameId)
        .collect(Collectors.toList());
    for (var ignoredGameId : ignoredGameIds) {
      final var recommendationToDelete =
          userRecommendationRepository.findAllByGameIdOrderByPredictedRatingDesc(ignoredGameId);
      userRecommendationRepository.deleteAll(recommendationToDelete);
    }

  }

  private Integer getMostUsedTopic(Dataset<Row> topicsDistributionDataset, String gameTitle) {

    var topics = topicsDistributionDataset.withColumn("topicIndex",
        callUDF("maxTopicIndex", col(Columns.SECOND_TOPIC_DISTRIBUTION)));

    return topics.filter(col(Columns.TITLE_COLUMN).notEqual(gameTitle))
        .first().<Integer>getAs("topicIndex");
  }
}
