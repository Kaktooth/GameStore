package com.store.gamestore.metrics.recommender;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.metrics.UsedItemInteractionCalculator;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserMetric;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoughtPrecisionMetric implements Metric {

  private final UsedItemInteractionCalculator usedItemInteractionCalculator;

  @Override
  public UserMetric calculateMetric(UUID userId) {
    var usedGames = usedItemInteractionCalculator.getUsedGamesInteractions(userId,
        InteractionType.BOUGHT, true);
    var notUsedGames = usedItemInteractionCalculator.getUsedGamesInteractions(userId,
        InteractionType.BOUGHT, true);
    double precision = usedGames / (usedGames + notUsedGames);
    var metricGenerationDate = LocalDateTime.now();
    return new UserMetric(UUID.randomUUID(), userId, precision, metricGenerationDate,
        getClass().getName());
  }
}