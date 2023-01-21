package com.store.gamestore.service.game.pictures;

import com.store.gamestore.persistence.entity.GamePicture;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.UUID;

public interface GamePictureService {

  GamePicture findGamePictureByGameIdAndPictureTypeId(UUID gameId, Integer typeId);

  List<GamePicture> findGamePictureByGameIdsAndPictureTypeId(Iterable<UUID> gameIds,
      Integer typeId);

  List<GamePicture> findGameplayPicturesByGameId(UUID gameId);
}
