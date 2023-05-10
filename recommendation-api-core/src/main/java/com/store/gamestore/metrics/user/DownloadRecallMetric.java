package com.store.gamestore.metrics.user;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.metrics.UserMetric;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.MetricType;
import com.store.gamestore.service.UserInteractionsService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadRecallMetric implements UserMetric {

  private final UserInteractionsService userInteractionsService;

  @Override
  public CalculatedMetric calculateMetric(UUID userId) {
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
    return new CalculatedMetric(UUID.randomUUID(), userId, recall, metricName, "",
        MetricType.USER);
  }
}
