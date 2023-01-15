package com.store.gamestore.service.game.genre;

import com.store.gamestore.persistence.entity.GameGenre;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameGenreService extends AbstractService<GameGenre, UUID> {

  public GameGenreService(CommonRepository<GameGenre, UUID> repository) {
    super(repository);
  }
}
