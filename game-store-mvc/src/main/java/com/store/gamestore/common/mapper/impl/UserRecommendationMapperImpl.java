package com.store.gamestore.common.mapper.impl;

import com.store.gamestore.common.mapper.UserRecommendationMapper;
import com.store.gamestore.model.dto.UserRecommendationDTO;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.UserRecommendation;
import com.store.gamestore.service.game.pictures.GamePictureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRecommendationMapperImpl implements UserRecommendationMapper {

  private final GamePictureService gamePictureService;

  @Override
  public UserRecommendationDTO sourceToDestination(UserRecommendation userRecommendation) {
    GamePicture gamePicture = gamePictureService.findGamePictureByGameIdAndPictureTypeId(
        userRecommendation.getGameId(), GamePictureType.STORE.ordinal());
    return new UserRecommendationDTO(userRecommendation, gamePicture);
  }

  @Override
  public List<UserRecommendationDTO> sourceToDestination(
      List<UserRecommendation> userRecommendations) {
    return userRecommendations.stream().map(this::sourceToDestination).toList();
  }
}