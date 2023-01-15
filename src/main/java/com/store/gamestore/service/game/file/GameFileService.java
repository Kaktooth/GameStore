package com.store.gamestore.service.game.file;

import com.store.gamestore.persistence.entity.GameFile;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameFileService extends AbstractService<GameFile, UUID> {

  public GameFileService(CommonRepository<GameFile, UUID> repository) {
    super(repository);
  }
}
