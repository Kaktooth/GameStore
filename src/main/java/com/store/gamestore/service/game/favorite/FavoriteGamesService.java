package com.store.gamestore.service.game.favorite;

import com.store.gamestore.persistence.entity.FavoriteGame;
import java.util.List;
import java.util.UUID;

public interface FavoriteGamesService {

  List<FavoriteGame> findFavoriteGamesByUserId(UUID userId);
}
