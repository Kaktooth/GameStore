package com.store.gamestore.metrics.developer;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserMetric;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisitedRecommendedGameMetric implements Metric {

  private final UserInteractionRepository userInteractionRepository;

  @Override
  public UserMetric calculateMetric(UUID gameId) {
    var userInteractions = userInteractionRepository.countAllGameInteractions(gameId,
        InteractionType.VISITED, true).doubleValue();
    var metricGenerationDate = LocalDateTime.now();
    return new UserMetric(UUID.randomUUID(), gameId, userInteractions, metricGenerationDate,
        getClass().getName());
  }
}
