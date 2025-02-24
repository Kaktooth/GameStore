package com.store.gamestore.persistence.entity;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_games_collection")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserGame extends Domain {

  @Column(name = "user_id")
  private UUID userId;
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "game_id")
  private Game game;
}
