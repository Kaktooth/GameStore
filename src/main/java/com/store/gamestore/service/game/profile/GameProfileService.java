package com.store.gamestore.service.game.profile;

import com.store.gamestore.model.entity.GameProfile;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameProfileService extends AbstractService<GameProfile, Integer> {

    public GameProfileService(CommonRepository<GameProfile, Integer> repository) {
        super(repository);
    }
}
