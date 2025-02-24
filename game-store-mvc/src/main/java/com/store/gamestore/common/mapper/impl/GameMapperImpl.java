package com.store.gamestore.common.mapper.impl;

import com.store.gamestore.common.mapper.GameMapper;
import com.store.gamestore.model.dto.GameDTO;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.service.game.pictures.GamePictureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapperImpl implements GameMapper {

  private final GamePictureService gamePictureService;

  @Override
  public GameDTO sourceToDestination(Game game) {
    GamePicture gamePicture = gamePictureService.findGamePictureByGameIdAndPictureTypeId(
        game.getId(), GamePictureType.STORE.ordinal());
    return new GameDTO(game, gamePicture);
  }

  @Override
  public List<GameDTO> sourceToDestination(List<Game> games) {
    return games.stream().map(this::sourceToDestination).toList();
  }
}
