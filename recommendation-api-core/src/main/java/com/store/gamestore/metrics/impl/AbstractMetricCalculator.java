package com.store.gamestore.metrics.impl;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.metrics.MetricCalculator;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.repository.MetricRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMetricCalculator<T extends Metric> implements MetricCalculator {

  private final MetricRepository metricRepository;
  private final List<T> metrics;

  @Override
  public void calculateMetrics(UUID referenceId) {
    log.info("calculate metrics for: {}", referenceId);
    metrics.forEach(metric -> {
      var calculatedMetric = metric.calculateMetric(referenceId);
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

  @Override
  public List<CalculatedMetric> getCalculatedMetrics(UUID referenceId) {
    return metricRepository.findAllByReferenceId(referenceId);
  }
}