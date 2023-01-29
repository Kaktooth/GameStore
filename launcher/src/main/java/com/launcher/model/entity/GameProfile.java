package com.launcher.model.entity;

import java.time.LocalDate;
import java.util.UUID;

public class GameProfile {

  private LocalDate releaseDate;
  private String description;
  private String briefDescription;
  private UUID gameId;


  public void setGameId(UUID gameId) {
    this.gameId = gameId;
  }

  public void setBriefDescription(String briefDescription) {
    this.briefDescription = briefDescription;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }
}
