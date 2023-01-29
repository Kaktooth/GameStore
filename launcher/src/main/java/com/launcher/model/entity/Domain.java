package com.launcher.model.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import java.util.UUID;

@JsonSubTypes({
    @Type(value = User.class, name = "user"),
    @Type(value = Game.class, name = "game"),
    @Type(value = GameProfile.class, name = "gameProfile"),
    @Type(value = GameFile.class, name = "gameFile"),
    @Type(value = UserGame.class, name = "userGame"),
    @Type(value = UserGameDTO.class, name = "userGameDto")
})
public abstract class Domain {

  private UUID id;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
