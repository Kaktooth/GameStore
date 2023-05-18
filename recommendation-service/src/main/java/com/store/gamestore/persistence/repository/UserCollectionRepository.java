package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.UserGame;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserCollectionRepository extends CrudRepository<UserGame, UUID> {

  List<UserGame> findAllByUserId(UUID userId);

  Boolean existsByGameIdAndUserId(UUID gameId, UUID userId);

  UserGame findByGameId(UUID gameId);
}