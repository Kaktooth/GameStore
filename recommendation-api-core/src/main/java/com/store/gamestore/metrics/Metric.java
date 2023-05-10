package com.store.gamestore.metrics;

import com.store.gamestore.persistence.entity.CalculatedMetric;
import java.util.UUID;

public interface Metric {

  CalculatedMetric calculateMetric(UUID referenceId);
}
