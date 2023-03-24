package com.store.gamestore.metrics.recommender;

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
public class BoughtRecallMetric implements Metric {

  private final UserInteractionRepository userInteractionRepository;

  @Override
  public UserMetric calculateMetric(UUID userId) {

    var recommendedGames = userInteractionRepository.findAllUserInteractions(
        userId.toString(), InteractionType.BOUGHT, true).size();
    var notRecommendedGames = userInteractionRepository.findAllUserInteractions(
        userId.toString(), InteractionType.BOUGHT, false).size();
    double recall = recommendedGames / (recommendedGames + notRecommendedGames);
    var metricGenerationDate = LocalDateTime.now();
    return new UserMetric(UUID.randomUUID(), userId, recall, metricGenerationDate,
        getClass().getName());
  }
}
