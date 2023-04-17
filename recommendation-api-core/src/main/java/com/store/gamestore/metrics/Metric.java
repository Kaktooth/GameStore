package com.store.gamestore.metrics;

import java.util.UUID;

public interface Metric {

  com.store.gamestore.persistence.entity.Metric calculateMetric(UUID id);
}
