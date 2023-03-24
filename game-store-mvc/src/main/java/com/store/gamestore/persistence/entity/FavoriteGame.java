package com.store.gamestore.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "favorite_games")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteGame extends Domain {

  @Column(name = "user_id")
  private UUID userId;
  @OneToOne
  @JoinColumn(name = "game_id")
  private Game game;

}
