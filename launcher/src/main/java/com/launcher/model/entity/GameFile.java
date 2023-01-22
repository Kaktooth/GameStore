package com.launcher.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;

@JsonIgnoreProperties(value = {"file"})
public class GameFile extends Domain {

  private String name;
  private String version;
  private UUID gameId;

  @Override
  public void setId(UUID id) {
    super.setId(id);
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setGameId(UUID gameId) {
    this.gameId = gameId;
  }
}
