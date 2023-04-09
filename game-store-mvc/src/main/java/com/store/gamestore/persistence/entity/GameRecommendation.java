package com.store.gamestore.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameRecommendation implements Serializable {

  @Serial
  private static final long serialVersionUID = 4308029746548507001L;

  private UUID id;
  private UUID firstGameId;
  private UUID secondGameId;
  private Double similarity;
}
