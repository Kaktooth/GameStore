package com.store.gamestore.service;

import com.store.gamestore.persistence.entity.Metric;
import java.util.List;
import java.util.UUID;

public interface MetricService {

  void calculateMetrics();

  List<Metric> getMetricsByReferenceId(UUID refId);

  Metric getMetricByReferenceIdAndName(UUID refId, String metricName);
}
