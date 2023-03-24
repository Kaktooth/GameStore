package com.store.gamestore.metrics.impl;

import com.store.gamestore.metrics.MetricCalculator;
import com.store.gamestore.metrics.Metric;
import com.store.gamestore.persistence.repository.MetricRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetricCalculatorImpl implements MetricCalculator {

  private final MetricRepository metricRepository;
  private final List<Metric> metrics;

  @Override
  public void calculateMetrics(UUID userId) {
    metrics.forEach(metric -> metricRepository.save(metric.calculateMetric(userId)));
  }
}