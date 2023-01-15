package com.store.gamestore.service.game.profile;

import com.store.gamestore.persistence.entity.GameProfile;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameProfileService extends AbstractService<GameProfile, UUID> {

  public GameProfileService(
      CommonRepository<GameProfile, UUID> repository) {
    super(repository);
  }
}
