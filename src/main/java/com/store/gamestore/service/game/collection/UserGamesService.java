package com.store.gamestore.service.game.collection;

import com.store.gamestore.persistence.entity.UploadedGame;
import com.store.gamestore.persistence.entity.UserGame;
import java.util.List;
import java.util.UUID;

public interface UserGamesService {

  List<UserGame> findAllByUserId(UUID userId);

  UserGame findByGameId(UUID gameId);
}
