package com.store.gamestore.metrics.impl;

import com.store.gamestore.metrics.UserMetric;
import com.store.gamestore.persistence.repository.MetricRepository;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class UserMetricsCalculatorImpl extends AbstractMetricCalculator<UserMetric> {

  public UserMetricsCalculatorImpl(MetricRepository metricRepository, List<UserMetric> metrics) {
    super(metricRepository, metrics);
  }
}
