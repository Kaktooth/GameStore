package com.store.gamestore.model.dto;


import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GamePicture;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDTO {

  private Game game;

  private GamePicture gamePicture;

}
