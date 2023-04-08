package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@Document(collection = "user-recommendations")
public class UserRecommendation implements Serializable {

  private static final long serialVersionUID = -6708530972415161759L;

  @MongoId
  private UUID id;
  private Double predictedRating;
  private LocalDateTime recommendationDate;
  private UUID userId;
  private UUID gameId;
  private Integer topicId;
}
