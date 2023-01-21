package com.store.gamestore.model.dto;


import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GamePicture;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDTO implements Serializable {

  private Game game;

  private GamePicture gamePicture;

}
