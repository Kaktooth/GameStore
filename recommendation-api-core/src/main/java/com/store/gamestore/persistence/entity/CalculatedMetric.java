package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "metric-values")
public class CalculatedMetric implements Serializable {

  private static final long serialVersionUID = 3184071260793018582L;

  UUID id;
  UUID referenceId;
  Double value;
  String metricName;
  String recommender;
  MetricType metricType;
}
