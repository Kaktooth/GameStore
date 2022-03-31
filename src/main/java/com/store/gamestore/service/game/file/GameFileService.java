package com.store.gamestore.service.game.file;

import com.store.gamestore.model.GameFile;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameFileService extends AbstractService<GameFile, Integer> {
    public GameFileService(CommonRepository<GameFile, Integer> gameRepository) {
        super(gameRepository);
    }
}
