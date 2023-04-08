package com.store.gamestore.metrics.impl;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.metrics.MetricCalculator;
import com.store.gamestore.persistence.repository.MetricRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetricCalculatorImpl implements MetricCalculator {

  private final MetricRepository metricRepository;
  private final List<Metric> metrics;

  @Override
  public void calculateMetrics(UUID userId) {
    log.info("calculate metrics for user: {}", userId);
    metrics.forEach(metric -> {
      var calculatedMetric = metric.calculateMetric(userId);
      var foundMetric = metricRepository.findByReferenceIdAndMetricName(
          calculatedMetric.getReferenceId(),
          calculatedMetric.getMetricName());
      if (foundMetric != null) {
        foundMetric.setValue(calculatedMetric.getValue());
        metricRepository.save(foundMetric);
      } else {
        metricRepository.save(calculatedMetric);
      }
    });
  }
}