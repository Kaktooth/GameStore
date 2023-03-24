package com.store.gamestore.metrics;

import com.store.gamestore.persistence.entity.UserMetric;
import java.util.UUID;

public interface Metric {

  UserMetric calculateMetric(UUID id);
}
