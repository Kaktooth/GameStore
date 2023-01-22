package com.store.gamestore.model.util;

import com.store.gamestore.model.dto.UserGameDTO;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.persistence.entity.UserGame;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGameMapperImpl implements UserGameMapper {

  private final UserService userService;
  private final GamePictureService gamePictureService;

  @Override
  public UserGameDTO sourceToDestination(UserGame userGame) {
    User user = userService.get(userGame.getUserId());
    GamePicture gamePicture = gamePictureService.findGamePictureByGameIdAndPictureTypeId(
        userGame.getGame().getId(), GamePictureType.GAME_PAGE.ordinal());

    return new UserGameDTO(user, userGame.getGame(), gamePicture.getImage());
  }

  @Override
  public List<UserGameDTO> sourceToDestination(List<UserGame> userGames) {
    return userGames.stream().map(this::sourceToDestination).toList();
  }
}
