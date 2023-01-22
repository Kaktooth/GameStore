package com.launcher.model.entity;


import java.util.UUID;

public class UserGame extends Domain {

  private User user;
  private Game game;

  @Override
  public void setId(UUID id) {
    super.setId(id);
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
