package com.launcher.model.entity;

import java.io.ByteArrayInputStream;
import java.util.UUID;

public class GameBlob extends Domain {

  private ByteArrayInputStream bytes;
  private String version;
  private String name;

  @Override
  public void setId(UUID id) {
    super.setId(id);
  }

  public void setBytes(ByteArrayInputStream bytes) {
    this.bytes = bytes;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
