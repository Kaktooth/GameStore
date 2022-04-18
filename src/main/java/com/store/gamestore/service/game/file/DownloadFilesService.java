package com.store.gamestore.service.game.file;

import com.store.gamestore.model.entity.GameBlob;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DownloadFilesService extends AbstractService<GameBlob, UUID> {
    public DownloadFilesService(CommonRepository<GameBlob, UUID> gameRepository) {
        super(gameRepository);
    }
}
