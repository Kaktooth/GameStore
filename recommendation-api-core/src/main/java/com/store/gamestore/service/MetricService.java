package com.store.gamestore.service;

import com.store.gamestore.persistence.entity.UserMetric;
import java.util.List;
import java.util.UUID;

public interface MetricService {

  void calculateMetrics();

  List<UserMetric> getAllMetricsByReferenceId(UUID refId);

  List<UserMetric> getMetricsByReferenceIdAndName(UUID refId, String metricName);
}