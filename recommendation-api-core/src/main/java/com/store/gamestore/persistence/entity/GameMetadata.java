package com.store.gamestore.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "games")
public class GameMetadata {

  @Id
  private UUID id;
  private String title;
  private BigDecimal price;
  private String developer;
  private String publisher;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "game_genres",
      joinColumns = {@JoinColumn(name = "game_id")},
      inverseJoinColumns = {@JoinColumn(name = "genre_id")}
  )
  private List<GameCategory> gameCategories;
  private String description;
  private LocalDate releaseDate;
}
