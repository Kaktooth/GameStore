package com.store.gamestore.metrics.user;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.metrics.UsedItemInteractionCalculator;
import com.store.gamestore.metrics.UserMetric;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.MetricType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FalsePositiveBoughtRate implements UserMetric {

  private final UsedItemInteractionCalculator usedItemInteractionCalculator;

  @Override
  public CalculatedMetric calculateMetric(UUID userId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating: {}", metricName);
    var notUsedRecommendedGames = usedItemInteractionCalculator.getUsedGamesInteractions(userId,
        InteractionType.BOUGHT, true);
    var notUsedGames = usedItemInteractionCalculator.getNotUsedGamesInteractions(userId,
        InteractionType.BOUGHT, false);
    var rate = 0.0d;
    try {
      rate = notUsedRecommendedGames / (notUsedRecommendedGames + notUsedGames);
    } catch (ArithmeticException exception) {
      log.error(exception.toString());
    }
    return new CalculatedMetric(UUID.randomUUID(), userId, rate, metricName, "",
        MetricType.USER);
  }
}