package com.store.gamestore.service.metrics;

import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.consumer.KafkaLatestRecordConsumer;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.service.game.GameService;
import com.store.gamestore.service.game.uploaded.UploadedGameService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricsServiceImpl implements MetricsService {

  private final KafkaLatestRecordConsumer<HashMap<String, List<CalculatedMetric>>> metricsConsumer;
  private final KafkaLatestRecordConsumer<HashMap<UUID, List<CalculatedMetric>>> metricsWithIdConsumer;
  private final UploadedGameService uploadedGameService;
  private final GameService gameService;

  @Override
  public Map<String, List<CalculatedMetric>> getCalculatedRecommenderMetrics() {
    return metricsConsumer.getRecord(KafkaTopics.RECOMMENDER_METRICS_ID);
  }

  @Override
  public Map<String, List<CalculatedMetric>> getCalculatedUserMetrics() {
    return metricsConsumer.getRecord(KafkaTopics.USER_METRICS_ID);
  }

  @Override
  public Map<Game, List<CalculatedMetric>> getCalculatedGamesMetrics(UUID authenticatedUserId) {
    var uploadedGamesIds = uploadedGameService.findAllByUserId(authenticatedUserId).stream()
        .map(uploadedGame -> uploadedGame.getGame().getId())
        .toList();
    var metrics = new HashMap<Game, List<CalculatedMetric>>();
    metricsWithIdConsumer.getRecord(KafkaTopics.GAME_METRICS_ID).entrySet().stream()
        .filter(gameMetrics -> uploadedGamesIds.contains(gameMetrics.getKey()))
        .forEach(entry -> metrics.put(gameService.get(entry.getKey()), entry.getValue()));
    return metrics;
  }
}
