package com.store.gamestore.model.util;

import com.store.gamestore.model.dto.UploadedGameDTO;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.UploadedGame;
import com.store.gamestore.service.game.pictures.GamePictureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UploadedGameMapperImpl implements UploadedGameMapper {

  private final GamePictureService gamePictureService;

  @Override
  public UploadedGameDTO sourceToDestination(UploadedGame uploadedGame) {
    GamePicture gamePicture = gamePictureService.findGamePictureByGameIdAndPictureTypeId(
        uploadedGame.getGame().getId(), GamePictureType.STORE.ordinal());
    return new UploadedGameDTO(uploadedGame, gamePicture);
  }

  @Override
  public List<UploadedGameDTO> sourceToDestination(List<UploadedGame> uploadedGames) {
    return uploadedGames.stream().map(this::sourceToDestination).toList();
  }
}
