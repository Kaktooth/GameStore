package com.store.gamestore.persistence.repository.game.collection;

import com.store.gamestore.persistence.entity.UserGame;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.List;
import java.util.UUID;

public interface UserGamesRepository extends CommonRepository<UserGame, UUID> {

  List<UserGame> findAllByUserId(UUID userId);

  Boolean findByGameIdAndUserId(UUID gameId, UUID userId);

  UserGame findByGameId(UUID gameId);
}