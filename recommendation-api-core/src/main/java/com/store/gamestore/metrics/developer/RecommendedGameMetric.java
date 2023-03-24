package com.store.gamestore.metrics.developer;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.metrics.UsedItemInteractionCalculator;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserMetric;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendedGameMetric implements Metric {

  private final UsedItemInteractionCalculator usedItemInteractionCalculator;

  @Override
  public UserMetric calculateMetric(UUID gameId) {
    var usedGames = usedItemInteractionCalculator.getUsedItemInteractions(gameId,
        InteractionType.DOWNLOADED, true);
    var notUsedGames = usedItemInteractionCalculator.getNotUsedItemInteractions(gameId,
        InteractionType.DOWNLOADED, true);
    return null;
  }
}
