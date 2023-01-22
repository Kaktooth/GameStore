package com.launcher.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameImage extends Image {

  private UUID gameId;

  private Integer pictureTypeId;

  private Image image;

  public void setGameId(UUID gameId) {
    this.gameId = gameId;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public void setImageData(byte[] imageData) {
    super.setImageData(imageData);
  }

  public void setPictureTypeId(Integer pictureTypeId) {
    this.pictureTypeId = pictureTypeId;
  }
}
