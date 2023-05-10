package com.store.gamestore.metrics.game;

import com.store.gamestore.metrics.GameMetric;
import com.store.gamestore.metrics.Metric;
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
public class IgnoredGameMetric implements GameMetric {

  private final UserInteractionsService userInteractionsService;

  @Override
  public CalculatedMetric calculateMetric(UUID gameId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating: {}", metricName);
    var userInteractions = userInteractionsService.countAllUserInteractionsWithGame(gameId,
        InteractionType.IGNORED).orElse(0).doubleValue();
    return new CalculatedMetric(UUID.randomUUID(), gameId, userInteractions, metricName, "",
        MetricType.GAME);
  }
}
