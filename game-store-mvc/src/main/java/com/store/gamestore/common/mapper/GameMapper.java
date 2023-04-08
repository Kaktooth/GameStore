package com.store.gamestore.common.mapper;

import com.store.gamestore.model.dto.GameDTO;
import com.store.gamestore.persistence.entity.Game;
import java.util.List;

public interface GameMapper {

  GameDTO sourceToDestination(Game game);

  List<GameDTO> sourceToDestination(List<Game> games);
}
