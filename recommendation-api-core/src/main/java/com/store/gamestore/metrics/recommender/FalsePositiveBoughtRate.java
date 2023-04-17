package com.store.gamestore.metrics.recommender;

import com.store.gamestore.metrics.UsedItemInteractionCalculator;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.Metric;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FalsePositiveBoughtRate implements com.store.gamestore.metrics.Metric {

  private final UsedItemInteractionCalculator usedItemInteractionCalculator;

  @Override
  public Metric calculateMetric(UUID userId) {
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
    return new Metric(UUID.randomUUID(), userId, rate, metricName);
  }
}