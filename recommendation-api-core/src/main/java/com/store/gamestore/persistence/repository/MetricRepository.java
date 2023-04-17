package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.Metric;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetricRepository extends MongoRepository<Metric, UUID> {

  List<Metric> findAllByReferenceId(UUID refId);

  Metric findByReferenceIdAndMetricName(UUID refId, String metricName);
}
