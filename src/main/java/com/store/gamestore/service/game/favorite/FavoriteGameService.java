package com.store.gamestore.service.game.favorite;

import com.store.gamestore.persistence.entity.FavoriteGame;
import com.store.gamestore.service.CommonService;
import java.util.List;
import java.util.UUID;

public interface FavoriteGameService extends CommonService<FavoriteGame, UUID> {

  List<FavoriteGame> findAllByUserId(UUID userId);

  FavoriteGame findByGameId(UUID gameId);

  Boolean existsByGameIdAndUserId(UUID gameId, UUID userId);

  void deleteByGameIdAndUserId(UUID gameId, UUID userId);
}
