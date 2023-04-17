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
public class DownloadPrecisionMetric implements com.store.gamestore.metrics.Metric {

  private final UsedItemInteractionCalculator usedItemInteractionCalculator;

  @Override
  public Metric calculateMetric(UUID userId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating: {}", metricName);
    var usedGames = usedItemInteractionCalculator.getUsedGamesInteractions(userId,
        InteractionType.DOWNLOADED, true);
    var notUsedGames = usedItemInteractionCalculator.getUsedGamesInteractions(userId,
        InteractionType.DOWNLOADED, true);
    var precision = 0.0d;
    try {
      precision = usedGames / (usedGames + notUsedGames);
    } catch (ArithmeticException exception) {
      log.error(exception.toString());
    }
    return new Metric(UUID.randomUUID(), userId, precision, metricName);
  }
}