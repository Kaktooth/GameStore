package com.store.gamestore.service.game.uploaded;

import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UploadedGamesService extends AbstractService<UploadedGame, UUID> {
    public UploadedGamesService(CommonRepository<UploadedGame, UUID> repository) {
        super(repository);
    }
}
