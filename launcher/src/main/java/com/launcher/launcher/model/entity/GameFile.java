package com.launcher.launcher.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"multipartFile"})
public class GameFile {

  private Integer id;
  private Integer objectId;
  private String name;
  private String version;
  private UUID gameId;
}
