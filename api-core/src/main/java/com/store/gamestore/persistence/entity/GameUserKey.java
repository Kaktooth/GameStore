package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class GameUserKey implements Serializable {

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "game_id")
  private UUID gameId;
}
