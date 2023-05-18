package com.store.gamestore.service.impl;

import com.store.gamestore.common.ApplicationConstants.KafkaTopics;
import com.store.gamestore.common.ApplicationConstants.Qualifiers;
import com.store.gamestore.common.ApplicationConstants.RecommenderConstants;
import com.store.gamestore.persistence.entity.GameRecommendation;
import com.store.gamestore.persistence.entity.UserGame;
import com.store.gamestore.persistence.entity.UserRecommendation;
import com.store.gamestore.persistence.repository.GameMetadataRepository;
import com.store.gamestore.persistence.repository.GameRecommendationRepository;
import com.store.gamestore.persistence.repository.UserCollectionRepository;
import com.store.gamestore.persistence.repository.UserRecommendationRepository;
import com.store.gamestore.persistence.repository.UserRepository;
import com.store.gamestore.recommender.GameRecommendationFilter;
import com.store.gamestore.recommender.Recommender;
import com.store.gamestore.recommender.impl.GameRecommendationFiltererImpl;
import com.store.gamestore.service.RecommenderService;
import com.store.gamestore.service.TopicService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class ScheduledRecommenderServiceImpl implements RecommenderService {

  @Qualifier(Qualifiers.LDA_RECOMMENDER)
  private final Recommender ldaRecommender;

  private final KafkaTemplate<UUID, List<UserRecommendation>> userRecommendationTemplate;
  private final KafkaTemplate<UUID, List<GameRecommendation>> gameRecommendationTemplate;
  private final KafkaTemplate<UUID, Map<Integer, Set<String>>> topicsTemplate;
  private final UserRecommendationRepository userRecommendationRepository;
  private final GameRecommendationRepository gameRecommendationRepository;
  private final GameRecommendationFilter gameRecommendationFilter;
  private final GameMetadataRepository gameMetadataRepository;
  private final UserRepository userRepository;
  private final UserCollectionRepository userCollectionRepository;
  private final TopicService topicService;

  @Override
  @Scheduled(fixedDelay = RecommenderConstants.SCHEDULER_RATE)
  public void recommend() {
    ldaRecommender.recommend();

    var users = userRepository.findAll();
    for (var user : users) {
      var userId = user.getId();
      var recommendations = getRecommendationsByUserId(userId);
      var userCollection = userCollectionRepository.findAllByUserId(userId);
      var gamesIds = userCollection.stream().map(UserGame::getGameId).collect(Collectors.toList());

      recommendations = recommendations.stream()
          .filter(rec -> !gamesIds.contains(rec.getGameId()))
          .sorted(Comparator.comparing(UserRecommendation::getPredictedRating))
          .collect(Collectors.toList());
      userRecommendationTemplate.send(KafkaTopics.USER_RECOMMENDATIONS, userId, recommendations);
    }

    var games = gameMetadataRepository.findAll();
    for (var game : games) {
      var gameId = game.getId();
      var recommendedGames = gameRecommendationRepository.findAllByFirstGameIdOrderBySimilarity(
          gameId);
      var recommendations = new GameRecommendationFiltererImpl(recommendedGames).applyFilter(
          gameRecommendationFilter).collect();
      gameRecommendationTemplate.send(KafkaTopics.GAME_RECOMMENDATIONS, gameId, recommendations);
    }

    var topics = topicService.getTopics();
    topicsTemplate.send(KafkaTopics.TOPIC_VOCABULARY, KafkaTopics.TOPIC_VOCABULARY_ID, topics);
  }

  private List<UserRecommendation> getRecommendationsByUserId(UUID userId) {
    return userRecommendationRepository.findAllByUserIdOrderByPredictedRatingDesc(userId);
  }
}
