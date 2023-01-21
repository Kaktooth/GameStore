package com.launcher.launcher.model.entity;

import java.io.ByteArrayInputStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameBlob {

  private ByteArrayInputStream bytes;
  private String version;
  private String name;
}
