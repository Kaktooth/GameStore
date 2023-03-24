package com.store.gamestore.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "metric-values")
public class UserMetric {

  UUID id;
  UUID referenceId;
  Double value;
  LocalDateTime generationDate;
  String metricName;
}
