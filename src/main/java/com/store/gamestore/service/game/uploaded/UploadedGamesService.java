package com.store.gamestore.service.game.uploaded;

import com.store.gamestore.model.entity.UploadedGame;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@Qualifier("uploadedGameService")
public class UploadedGamesService extends AbstractService<UploadedGame, UUID> {
    public UploadedGamesService(CommonRepository<UploadedGame, UUID> repository) {
        super(repository);
    }
}
