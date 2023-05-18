package com.store.gamestore.service.impl;

import com.store.gamestore.common.ApplicationConstants.KafkaTopics;
import com.store.gamestore.common.ApplicationConstants.MetricsConstants;
import com.store.gamestore.metrics.impl.GameMetricsCalculatorImpl;
import com.store.gamestore.metrics.impl.RecommenderMetricsCalculatorImpl;
import com.store.gamestore.metrics.impl.UserMetricsCalculatorImpl;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.RecommenderType;
import com.store.gamestore.persistence.repository.GameTitleMetadataRepository;
import com.store.gamestore.persistence.repository.UserRepository;
import com.store.gamestore.service.MetricService;
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
  private final UserMetricsCalculatorImpl userMetricsCalculator;
  private final GameMetricsCalculatorImpl gameMetricsCalculator;
  private final RecommenderMetricsCalculatorImpl recommenderMetricsCalculator;
  private final UserRepository userRepository;
  private final GameTitleMetadataRepository gameRepository;

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
  }
}
