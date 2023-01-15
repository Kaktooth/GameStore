package com.store.gamestore.persistence.repository.game.favorite;

import com.store.gamestore.persistence.entity.FavoriteGame;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.List;
import java.util.UUID;

public interface FavoriteGameRepository extends CommonRepository<FavoriteGame, UUID> {
 List<FavoriteGame> findFavoriteGamesByUserId(UUID userId);
}