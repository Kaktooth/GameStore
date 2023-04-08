package com.store.gamestore.persistence.entity;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameRecommendation {

  private UUID id;
  private UUID firstGameId;
  private UUID secondGameId;
  private Double similarity;
}
