package com.launcher.model.entity;

public class UserGameDTO {

  private User user;
  private Game game;
  private Image image;

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public Game getGame() {
    return game;
  }


  public Image getImage() {
    return image;
  }

  public void setGame(Game game) {
    this.game = game;
  }


  public void setImage(GameImage image) {
    this.image = image;
  }
}
