package com.store.gamestore.service.game;

import com.store.gamestore.model.Game;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameService extends AbstractService<Game, Integer> {

    public GameService(CommonRepository<Game, Integer> gameRepository) {
        super(gameRepository);
    }
}
