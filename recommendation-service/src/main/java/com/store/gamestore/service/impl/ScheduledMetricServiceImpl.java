package com.store.gamestore.service.impl;

import com.store.gamestore.common.ApplicationConstants.KafkaTopics;
import com.store.gamestore.common.ApplicationConstants.MetricsConstants;
import com.store.gamestore.metrics.impl.GameMetricsCalculatorImpl;
import com.store.gamestore.metrics.impl.RecommenderMetricsCalculatorImpl;
import com.store.gamestore.metrics.impl.UserMetricsCalculatorImpl;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.RecommenderType;
import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.repository.GameTitleMetadataRepository;
import com.store.gamestore.persistence.repository.UserRepository;
import com.store.gamestore.service.MetricService;
import com.store.gamestore.service.UserInteractionsService;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledMetricServiceImpl implements MetricService {

  private final KafkaTemplate<UUID, HashMap<UUID, List<CalculatedMetric>>> calculatedMetricsWithId;
  private final KafkaTemplate<UUID, HashMap<String, List<CalculatedMetric>>> calculatedMetrics;
  private final KafkaTemplate<UUID, HashMap<UUID, List<UserInteraction>>> gameInteractions;
  private final UserMetricsCalculatorImpl userMetricsCalculator;
  private final GameMetricsCalculatorImpl gameMetricsCalculator;
  private final RecommenderMetricsCalculatorImpl recommenderMetricsCalculator;
  private final UserRepository userRepository;
  private final GameTitleMetadataRepository gameRepository;
  private final UserInteractionsService userInteractionsService;

  @Override
  @Scheduled(fixedDelay = MetricsConstants.SCHEDULER_RATE)
  public void calculateMetrics() {
    var userMetricsMap = new HashMap<String, List<CalculatedMetric>>();
    var users = userRepository.findAll();
    users.forEach(user -> {
      var userId = user.getId();
      userMetricsCalculator.calculateMetrics(userId);
      var userMetrics = userMetricsCalculator.getCalculatedMetrics(userId);
      userMetricsMap.put(user.getPublicUsername(), userMetrics);
    });
    calculatedMetrics.send(KafkaTopics.USER_METRICS, KafkaTopics.USER_METRICS_ID,
        userMetricsMap);

    var gameMetricsMap = new HashMap<UUID, List<CalculatedMetric>>();
    var games = gameRepository.findAll();
    games.forEach(game -> {
      var gameId = game.getId();
      gameMetricsCalculator.calculateMetrics(gameId);
      var userMetrics = gameMetricsCalculator.getCalculatedMetrics(gameId);
      gameMetricsMap.put(gameId, userMetrics);
    });
    calculatedMetricsWithId.send(KafkaTopics.GAME_METRICS, KafkaTopics.GAME_METRICS_ID,
        gameMetricsMap);

    var gameInteractionMap = new HashMap<UUID, List<UserInteraction>>();
    games.forEach(game -> {
      var gameId = game.getId();
      var gameInteractionList = userInteractionsService.findAllInteractionsWithGame(gameId);
      gameInteractionMap.put(gameId, gameInteractionList);
    });
    gameInteractions.send(KafkaTopics.USER_INTERACTION_METRICS,
        KafkaTopics.USER_INTERACTION_METRICS_ID,
        gameInteractionMap);

    var recommenderMetricsMap = new HashMap<String, List<CalculatedMetric>>();
    var recommenders = RecommenderType.values();
    for (var recommender : recommenders) {
      var recId = recommender.getId();
      recommenderMetricsCalculator.calculateMetrics(recId);
      var recommenderMetrics = gameMetricsCalculator.getCalculatedMetrics(recId);
      recommenderMetricsMap.put(recommender.getName(), recommenderMetrics);
    }
    calculatedMetrics.send(KafkaTopics.RECOMMENDER_METRICS, KafkaTopics.RECOMMENDER_METRICS_ID,
        recommenderMetricsMap);

//    var gameInteractionsMap = new HashMap<UUID, HashMap<InteractionType, HashMap<LocalDate, Integer>>>();
//    games.forEach(game -> {
//      var gameId = game.getId();
//      var gameInteractionMap = new HashMap<InteractionType, HashMap<LocalDate, Integer>>();
//      Arrays.stream(InteractionType.values()).forEach(
//          interaction -> {
//            var yearInteractions = new HashMap<LocalDate, Integer>();
//            var now = LocalDate.now();
//
//            for (int i = 0; i < Month.values().length; i++) {
//              var startDate = LocalDate.of(now.getYear(), now.getMonth(), 1);
//              var endDate = LocalDate.of(now.getYear(), now.getMonth(), now.getMonth().maxLength());
//              var gameInteractions = userInteractionsService.countAllGameInteractionsByDate(
//                  gameId, interaction, startDate, endDate);
//              yearInteractions.put(startDate, gameInteractions.orElse(0));
//              now = now.minusMonths(1);
//            }
//            gameInteractionMap.put(interaction, yearInteractions);
//          }
//      );
//      gameInteractionsMap.put(gameId, gameInteractionMap);
//    });
//    calculatedMetricsWithId.send(KafkaTopics.USER_INTERACTIONS_BY_DATE,
//        KafkaTopics.USER_INTERACTIONS_BY_DATE_ID,
//        gameInteractionsMap);
  }
}
