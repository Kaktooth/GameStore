package com.store.gamestore.metrics.impl;

import com.store.gamestore.metrics.GameMetric;
import com.store.gamestore.persistence.repository.MetricRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GameMetricsCalculatorImpl extends AbstractMetricCalculator<GameMetric> {

  public GameMetricsCalculatorImpl(MetricRepository metricRepository, List<GameMetric> metrics) {
    super(metricRepository, metrics);
  }
}
