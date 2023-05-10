package com.store.gamestore.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculatedMetric implements Serializable {


  @Serial
  private static final long serialVersionUID = 3184071260793018582L;
  UUID id;
  UUID referenceId;
  Double value;
  String metricName;
  String recommender;
  MetricType metricType;
}
