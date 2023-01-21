package com.store.gamestore.service.game.file;

import com.store.gamestore.persistence.entity.GameFile;
import com.store.gamestore.service.CommonService;
import java.util.List;
import java.util.UUID;

public interface GameFileService extends CommonService<GameFile, UUID> {

  List<GameFile> findAllByGameId(UUID gameId);

  GameFile getLatestFileVersionByGameId(UUID gameId);
}
