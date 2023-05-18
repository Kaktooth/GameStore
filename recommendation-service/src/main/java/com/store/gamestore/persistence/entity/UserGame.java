package com.store.gamestore.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_games_collection")
@AllArgsConstructor
@NoArgsConstructor
public class UserGame {

  @Id
  private UUID id;
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "game_id")
  private UUID gameId;
}
