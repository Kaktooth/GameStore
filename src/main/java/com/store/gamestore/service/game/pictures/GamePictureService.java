package com.store.gamestore.service.game.pictures;

import com.store.gamestore.persistence.entity.GamePicture;
import java.util.UUID;

public interface GamePictureService {

  GamePicture findGamePictureByGameIdAndPictureTypeId(UUID gameId, Integer typeId);
}
