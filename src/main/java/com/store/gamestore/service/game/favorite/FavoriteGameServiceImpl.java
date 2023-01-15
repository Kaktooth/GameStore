package com.store.gamestore.service.game.favorite;

import com.store.gamestore.persistence.entity.FavoriteGame;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.game.favorite.FavoriteGameRepository;
import com.store.gamestore.service.AbstractService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class FavoriteGameServiceImpl extends AbstractService<FavoriteGame, UUID> implements
    FavoriteGamesService {

  public FavoriteGameServiceImpl(
      CommonRepository<FavoriteGame, UUID> repository) {
    super(repository);
  }

  @Override
  public List<FavoriteGame> findFavoriteGamesByUserId(UUID userId) {
    return ((FavoriteGameRepository) repository).findFavoriteGamesByUserId(userId);
  }
}
