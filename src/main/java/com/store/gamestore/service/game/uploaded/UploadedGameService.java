package com.store.gamestore.service.game.uploaded;

import com.store.gamestore.persistence.entity.UploadedGame;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UploadedGameService extends AbstractService<UploadedGame, UUID> {

  public UploadedGameService(
      CommonRepository<UploadedGame, UUID> repository) {
    super(repository);
  }
}
