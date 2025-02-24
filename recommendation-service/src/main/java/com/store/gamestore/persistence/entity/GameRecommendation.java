package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "game-recommendations")
public class GameRecommendation implements Serializable {

  private static final long serialVersionUID = -8393886434462419486L;

  @MongoId
  private UUID id;
  private UUID firstGameId;
  private UUID secondGameId;
  private Double similarity;
}
