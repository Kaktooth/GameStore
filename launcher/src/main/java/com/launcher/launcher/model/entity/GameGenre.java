package com.launcher.launcher.model.entity;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameGenre {

  private Set<Genre> genres;
  private UUID gameId;

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Genre genre : genres) {
      result.append(genre.toString());
      if (genres.toArray()[genres.size() - 1] != genre) {
        result.append(", ");
      }
    }
    return result.toString();
  }
}
