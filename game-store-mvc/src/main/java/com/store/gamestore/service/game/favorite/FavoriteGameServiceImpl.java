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
    FavoriteGameService {

  public FavoriteGameServiceImpl(
      CommonRepository<FavoriteGame, UUID> repository) {
    super(repository);
  }

  @Override
  public List<FavoriteGame> findAllByUserId(UUID userId) {
    return ((FavoriteGameRepository) repository).findAllByUserId(userId);
  }

  @Override
  public FavoriteGame findByGameId(UUID gameId) {
    return ((FavoriteGameRepository) repository).findByGameId(gameId);
  }

  @Override
  public Boolean existsByGameIdAndUserId(UUID gameId, UUID userId) {
    return ((FavoriteGameRepository) repository).existsByGameIdAndUserId(gameId, userId);
  }

  @Override
  public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
    ((FavoriteGameRepository) repository).deleteByGameIdAndUserId(gameId, userId);
  }
}
