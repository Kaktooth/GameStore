package com.store.gamestore.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRecommendation {

  private UUID id;
  private Double predictedRating;
  private LocalDateTime recommendationDate;
  private UUID userId;
  private UUID gameId;
  private Integer topicId;
}
