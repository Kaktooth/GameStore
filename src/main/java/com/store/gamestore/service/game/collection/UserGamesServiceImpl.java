package com.store.gamestore.service.game.collection;

import com.store.gamestore.persistence.entity.UserGame;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.game.collection.UserGamesRepository;
import com.store.gamestore.service.AbstractService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserGamesServiceImpl extends AbstractService<UserGame, UUID> implements
    UserGamesService {

  public UserGamesServiceImpl(
      CommonRepository<UserGame, UUID> repository) {
    super(repository);
  }

  @Override
  public List<UserGame> findAllByUserId(UUID userId) {
    return ((UserGamesRepository) repository).findAllByUserId(userId);
  }

  @Override
  public Boolean existsByGameIdAndUserId(UUID gameId, UUID userId) {
    return ((UserGamesRepository) repository).existsByGameIdAndUserId(gameId, userId);
  }

  @Override
  public UserGame findByGameId(UUID gameId) {
    return ((UserGamesRepository) repository).findByGameId(gameId);
  }
}
