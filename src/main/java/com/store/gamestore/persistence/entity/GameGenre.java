package com.store.gamestore.persistence.entity;

import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "game_genres")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GameGenre extends Domain {

  @Column(name = "game_id")
  private UUID gameId;

  @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
  @JoinTable
  private Set<Genre> genres;

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (var genre : genres) {
      result.append(genre);
      if (genres.toArray()[genres.size() - 1] != genre) {
        result.append(", ");
      }
    }
    return result.toString();
  }
}
