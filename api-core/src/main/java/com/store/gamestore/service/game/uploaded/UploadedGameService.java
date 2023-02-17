package com.store.gamestore.service.game.uploaded;

import com.store.gamestore.persistence.entity.UploadedGame;
import java.util.List;
import java.util.UUID;

public interface UploadedGameService {

  List<UploadedGame> findAllByUserId(UUID userId);

  UploadedGame findByGameId(UUID gameId);
}
