package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.UserMetric;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetricRepository extends MongoRepository<UserMetric, UUID> {

  List<UserMetric> findAllByReferenceId(UUID refId);

  List<UserMetric> findAllByReferenceIdAndMetricName(UUID refId, String metricName);
}
