package com.store.gamestore.service.metrics;

import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.Game;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MetricsService {

  Map<String, List<CalculatedMetric>> getCalculatedRecommenderMetrics();

  Map<String, List<CalculatedMetric>> getCalculatedUserMetrics();

  Map<Game, List<CalculatedMetric>> getCalculatedGamesMetrics(UUID authenticatedUserId);
}
