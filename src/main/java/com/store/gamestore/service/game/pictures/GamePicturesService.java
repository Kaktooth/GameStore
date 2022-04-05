package com.store.gamestore.service.game.pictures;

import com.store.gamestore.model.GameImage;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GamePicturesService extends AbstractService<GameImage, UUID> {
    public GamePicturesService(CommonRepository<GameImage, UUID> repository) {
        super(repository);
    }
}
