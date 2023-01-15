package com.store.gamestore.model.dto;


import lombok.Data;

@Data
public class GameFilesDTO {

  private String fileName;

  private Long objectId;

  private String version;

  private String gameId;

}
