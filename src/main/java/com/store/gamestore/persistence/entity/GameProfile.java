package com.store.gamestore.persistence.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "game_profiles")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GameProfile extends Domain {

  @Column(name = "release_date", nullable = false)
  private LocalDate releaseDate;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "brief_description", nullable = false)
  private String briefDescription;

  @Column(name = "game_id")
  private UUID gameId;

}
