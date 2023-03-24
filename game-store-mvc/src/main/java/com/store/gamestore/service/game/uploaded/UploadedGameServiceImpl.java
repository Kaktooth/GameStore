package com.store.gamestore.service.game.uploaded;

import com.store.gamestore.persistence.entity.UploadedGame;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.game.uploaded.UploadedGameRepository;
import com.store.gamestore.service.AbstractService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UploadedGameServiceImpl extends AbstractService<UploadedGame, UUID>
    implements UploadedGameService {

  public UploadedGameServiceImpl(
      CommonRepository<UploadedGame, UUID> repository) {
    super(repository);
  }

  @Override
  public List<UploadedGame> findAllByUserId(UUID userId) {
    return ((UploadedGameRepository) repository).findAllByUserId(userId);
  }

  @Override
  public UploadedGame findByGameId(UUID gameId) {
    return ((UploadedGameRepository) repository).findByGameId(gameId);
  }
}
