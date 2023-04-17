package com.store.gamestore.metrics.recommender;

import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.Metric;
import com.store.gamestore.service.UserInteractionsService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadRecallMetric implements com.store.gamestore.metrics.Metric {

  private final UserInteractionsService userInteractionsService;

  @Override
  public Metric calculateMetric(UUID userId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating metric: {}", metricName);
    var recommendedGames = userInteractionsService.countAllUserInteractions(userId,
        InteractionType.DOWNLOADED, true).orElse(0);
    var notRecommendedGames = userInteractionsService.countAllUserInteractions(userId,
        InteractionType.DOWNLOADED, false).orElse(0);
    var recall = 0.0d;
    try {
      recall = recommendedGames / (recommendedGames + notRecommendedGames);
    } catch (ArithmeticException exception) {
      log.error(exception.toString());
    }
    return new Metric(UUID.randomUUID(), userId, recall, metricName);
  }
}
