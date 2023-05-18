package com.store.gamestore.metrics.impl;

import com.store.gamestore.metrics.RecommenderMetric;
import com.store.gamestore.persistence.repository.MetricRepository;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class RecommenderMetricsCalculatorImpl extends AbstractMetricCalculator<RecommenderMetric> {

  public RecommenderMetricsCalculatorImpl(MetricRepository metricRepository,
      List<RecommenderMetric> metrics) {
    super(metricRepository, metrics);
  }
}
