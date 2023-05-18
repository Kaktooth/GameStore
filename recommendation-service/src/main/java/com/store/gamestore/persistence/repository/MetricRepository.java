package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.CalculatedMetric;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetricRepository extends MongoRepository<CalculatedMetric, UUID> {

  List<CalculatedMetric> findAllByReferenceId(UUID refId);

  CalculatedMetric findByReferenceIdAndMetricName(UUID refId, String metricName);
}
