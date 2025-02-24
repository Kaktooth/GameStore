package com.store.gamestore.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculatedMetric implements Serializable {

  @Serial
  private static final long serialVersionUID = 3184071260793018582L;

  public CalculatedMetric(UUID id, UUID referenceId, BigDecimal value, String metricName,
      String recommender, MetricType metricType) {
    this.id = id;
    this.referenceId = referenceId;
    this.value = value;
    this.metricName = metricName;
    this.recommender = recommender;
    this.metricType = metricType;
  }

  private UUID id;
  private UUID referenceId;
  private BigDecimal value;
  private String metricName;
  private String recommender;
  private MetricType metricType;
  private MetricComparingType metricComparingType;
}
