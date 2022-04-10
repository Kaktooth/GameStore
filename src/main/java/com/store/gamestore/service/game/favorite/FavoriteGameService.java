package com.store.gamestore.service.game.favorite;

import com.store.gamestore.model.entity.FavoriteGame;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class FavoriteGameService extends AbstractService<FavoriteGame, UUID> {
    public FavoriteGameService(CommonRepository<FavoriteGame, UUID> gameRepository) {
        super(gameRepository);
    }
}