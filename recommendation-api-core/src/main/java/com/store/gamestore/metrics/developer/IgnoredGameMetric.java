package com.store.gamestore.metrics.developer;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserMetric;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import com.store.gamestore.service.UserInteractionsService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IgnoredGameMetric implements Metric {

  private final UserInteractionsService userInteractionsService;

  @Override
  public UserMetric calculateMetric(UUID gameId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating: {}", metricName);
    var userInteractions = userInteractionsService.countAllUserInteractionsWithGame(gameId,
        InteractionType.IGNORED).orElse(0).doubleValue();
    return new UserMetric(UUID.randomUUID(), gameId, userInteractions, metricName);
  }
}
