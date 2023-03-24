package com.store.gamestore.persistence.repository.game.pictures;

import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.repository.CommonRepository;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface GamePictureRepository extends CommonRepository<GamePicture, UUID> {
  GamePicture findGamePictureByGameIdAndPictureTypeId(UUID gameId, Integer pictureTypeId);

  @Query(nativeQuery = true, value = "SELECT * FROM game_pictures WHERE game_id = :#{#gameId}"
      + " AND picture_type_id = 3")
  List<GamePicture> findGameplayPicturesByGameId(@Param("gameId")UUID gameId);
}