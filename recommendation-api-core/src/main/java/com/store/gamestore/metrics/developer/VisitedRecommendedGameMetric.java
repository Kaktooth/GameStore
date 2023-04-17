package com.store.gamestore.metrics.developer;

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
public class VisitedRecommendedGameMetric implements com.store.gamestore.metrics.Metric {

  private final UserInteractionsService userInteractionsService;

  @Override
  public Metric calculateMetric(UUID gameId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating: {}", metricName);
    var userInteractions = userInteractionsService.countAllUserInteractionsWithGame(gameId,
        InteractionType.VISITED, true).orElse(0).doubleValue();
    return new Metric(UUID.randomUUID(), gameId, userInteractions, metricName);
  }
}
