package com.store.gamestore.persistence.repository.game.uploaded;

import com.store.gamestore.persistence.entity.UploadedGame;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.List;
import java.util.UUID;

public interface UploadedGameRepository extends CommonRepository<UploadedGame, UUID> {

  List<UploadedGame> findAllByUserId(UUID userId);

  UploadedGame findByGameId(UUID gameId);
}