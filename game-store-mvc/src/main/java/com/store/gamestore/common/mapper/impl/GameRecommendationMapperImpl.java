package com.store.gamestore.common.mapper.impl;

import com.store.gamestore.common.mapper.GameRecommendationMapper;
import com.store.gamestore.model.dto.GameRecommendationDTO;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.GameRecommendation;
import com.store.gamestore.service.game.GameService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameRecommendationMapperImpl implements GameRecommendationMapper {

  private final GamePictureService gamePictureService;
  private final GameService gameService;

  @Override
  public GameRecommendationDTO sourceToDestination(GameRecommendation gameRecommendation) {
    var gameId = gameRecommendation.getSecondGameId();
    GamePicture gamePicture = gamePictureService.findGamePictureByGameIdAndPictureTypeId(
        gameId, GamePictureType.STORE.ordinal());
    var game = gameService.get(gameId);
    return new GameRecommendationDTO(gameRecommendation, gamePicture, game.getTitle(),
        game.getPrice());
  }

  @Override
  public List<GameRecommendationDTO> sourceToDestination(
      List<GameRecommendation> gameRecommendations) {
    return gameRecommendations.stream().map(this::sourceToDestination).toList();
  }
}
