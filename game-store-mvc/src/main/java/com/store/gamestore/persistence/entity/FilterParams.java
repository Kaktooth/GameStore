package com.store.gamestore.persistence.entity;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterParams {

  private LocalDate releaseDate;
  private Double minGamesPrice;
  private Double maxGamesPrice;
}
