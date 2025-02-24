package com.store.gamestore.metrics.user;

import com.store.gamestore.metrics.UserMetric;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.MetricType;
import com.store.gamestore.service.UserInteractionsService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoughtRecallMetric implements UserMetric {

  private final UserInteractionsService userInteractionsService;

  @Override
  public CalculatedMetric calculateMetric(UUID userId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating: {}", metricName);
    var recommendedGames = BigDecimal.valueOf(userInteractionsService.countAllUserInteractions(userId,
        InteractionType.BOUGHT, true).orElse(0));
    var notRecommendedGames = BigDecimal.valueOf(userInteractionsService.countAllUserInteractions(userId,
        InteractionType.BOUGHT, false).orElse(0));
    var recall = BigDecimal.valueOf(0);
    try {
      recall = recommendedGames.divide(recommendedGames.add(notRecommendedGames), 3, RoundingMode.HALF_UP);
    } catch (ArithmeticException exception) {
      log.error(exception.toString());
    }
    return new CalculatedMetric(UUID.randomUUID(), userId, recall, metricName, "",
        MetricType.USER);
  }
}
