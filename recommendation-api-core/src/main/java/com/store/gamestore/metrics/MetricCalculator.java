package com.store.gamestore.metrics;

import java.util.UUID;

public interface MetricCalculator {

  void calculateMetrics(UUID userId);
}
