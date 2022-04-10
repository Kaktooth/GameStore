package com.store.gamestore.service.game;

import com.store.gamestore.model.entity.Game;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GameService extends AbstractService<Game, UUID> {
    public GameService(CommonRepository<Game, UUID> repository) {
        super(repository);
    }
}
