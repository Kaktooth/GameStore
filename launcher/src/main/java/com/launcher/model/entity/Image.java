package com.launcher.model.entity;

public abstract class Image {

  private byte[] imageData;

  public byte[] getImageData() {
    return imageData;
  }

  public void setImageData(byte[] imageData) {
    this.imageData = imageData;
  }
}
