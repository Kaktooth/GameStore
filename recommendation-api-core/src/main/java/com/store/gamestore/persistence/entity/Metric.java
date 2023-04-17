package com.store.gamestore.persistence.entity;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "metric-values")
public class Metric {

  UUID id;
  UUID referenceId;
  Double value;
  String metricName;
}
