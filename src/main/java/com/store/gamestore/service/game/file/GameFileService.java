package com.store.gamestore.service.game.file;

import com.store.gamestore.persistence.entity.GameFile;
import java.util.List;
import java.util.UUID;

public interface GameFileService {

  List<GameFile> findAllByGameId(UUID gameId);
}
