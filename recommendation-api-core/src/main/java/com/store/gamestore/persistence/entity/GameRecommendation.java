package com.store.gamestore.persistence.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "game-recommendations")
public class GameRecommendation {

  @MongoId
  private String id;
  private String firstGameId;
  private String secondGameId;
  private Double similarity;
  private LocalDateTime localDateTime;
}
