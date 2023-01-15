package com.store.gamestore.persistence.repository.game.pictures;

import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.UUID;

public interface GamePictureRepository extends CommonRepository<GamePicture, UUID> {
  GamePicture findGamePictureByGameIdAndPictureTypeId(UUID gameId, Integer pictureTypeId);
}