package com.store.gamestore.model.dto;


import com.store.gamestore.persistence.entity.Game;
import java.util.UUID;
import lombok.Data;

@Data
public class UploadedGameDTO {

  private UUID userId;

  private Game game;
}
