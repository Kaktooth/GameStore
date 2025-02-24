package com.store.gamestore.service.game.profile;

import com.store.gamestore.persistence.entity.GameProfile;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.game.profile.GameProfileRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameProfileServiceImpl extends AbstractService<GameProfile, UUID> implements
    GameProfileService {

  public GameProfileServiceImpl(CommonRepository<GameProfile, UUID> repository) {
    super(repository);
  }

  @Override
  public GameProfile findGameProfileByGameId(UUID gameId) {
    return ((GameProfileRepository) repository).findGameProfileByGameId(gameId);
  }
}
