package com.store.gamestore.service.game.file;

import com.store.gamestore.persistence.entity.GameFile;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.game.file.GameFileRepository;
import com.store.gamestore.service.AbstractService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameFileServiceImpl extends AbstractService<GameFile, UUID> implements
    GameFileService {

  public GameFileServiceImpl(CommonRepository<GameFile, UUID> repository) {
    super(repository);
  }

  @Override
  public List<GameFile> findAllByGameId(UUID gameId) {
    return ((GameFileRepository) repository).findAllByGameId(gameId);
  }

  @Override
  public GameFile getLatestFileByGameId(UUID gameId) {
    Integer fileVersion = ((GameFileRepository) repository).getLatestFileVersionByGameId(gameId);
    return ((GameFileRepository) repository).getLatestFile(gameId, fileVersion);
  }
}
