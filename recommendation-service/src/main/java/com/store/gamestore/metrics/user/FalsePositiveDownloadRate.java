package com.store.gamestore.metrics.user;

import com.store.gamestore.metrics.UsedItemInteractionCalculator;
import com.store.gamestore.metrics.UserMetric;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.MetricType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FalsePositiveDownloadRate implements UserMetric {

  private final UsedItemInteractionCalculator usedItemInteractionCalculator;

  @Override
  public CalculatedMetric calculateMetric(UUID userId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating: {}", metricName);
    var notUsedRecommendedGames = BigDecimal.valueOf(usedItemInteractionCalculator.getUsedGamesInteractions(userId,
        InteractionType.DOWNLOADED, true));
    var notUsedGames = BigDecimal.valueOf(usedItemInteractionCalculator.getNotUsedGamesInteractions(userId,
        InteractionType.DOWNLOADED, false));
    var rate = BigDecimal.valueOf(0);
    try {
      rate = notUsedRecommendedGames.divide(notUsedRecommendedGames.add(notUsedGames), 3, RoundingMode.HALF_UP);
    } catch (ArithmeticException exception) {
      log.error(exception.toString());
    }
    return new CalculatedMetric(UUID.randomUUID(), userId, rate, metricName, "",
        MetricType.USER);
  }
}