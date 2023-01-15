package com.store.gamestore.model.dto;


import java.util.Date;
import lombok.Data;

@Data
public class GameProfilesDTO {

  private Date releaseDate;

  private String description;

  private String briefDescription;

  private String gameId;

}
