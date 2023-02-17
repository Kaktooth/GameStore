package com.store.gamestore.service.game;

import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameService extends AbstractService<Game, UUID> {

  public GameService(CommonRepository<Game, UUID> repository) {
    super(repository);
  }
}
