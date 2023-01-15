package com.store.gamestore.model.dto;


import java.util.UUID;
import lombok.Data;

@Data
public class FavoriteGameDTO {

  private UUID userId;

  private UUID gameId;

}
