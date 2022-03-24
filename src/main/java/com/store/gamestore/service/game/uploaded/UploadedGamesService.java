package com.store.gamestore.service.game.uploaded;

import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class UploadedGamesService extends AbstractService<UploadedGame, UUID> {
    public UploadedGamesService(CommonRepository<UploadedGame, UUID> repository) {
        super(repository);
    }

    @Override
    public Set<UploadedGame> getAll(UUID id) {
        Set<UploadedGame> uploadedGames = super.getAll(id);
        log.info(uploadedGames.toString());
        return uploadedGames;
    }
}
