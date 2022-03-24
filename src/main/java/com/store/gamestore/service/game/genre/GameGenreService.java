package com.store.gamestore.service.game.genre;

import com.store.gamestore.model.GameGenre;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GameGenreService extends AbstractService<GameGenre, UUID> {
    public GameGenreService(CommonRepository<GameGenre, UUID> repository) {
        super(repository);
    }
}
