package com.store.gamestore.metrics;

import com.store.gamestore.persistence.entity.CalculatedMetric;
import java.util.List;
import java.util.UUID;

public interface MetricCalculator {

  void calculateMetrics(UUID referenceId);

  List<CalculatedMetric> getCalculatedMetrics(UUID referenceId);
}
