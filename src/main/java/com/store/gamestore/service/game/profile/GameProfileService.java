package com.store.gamestore.service.game.profile;

import com.store.gamestore.persistence.entity.GameProfile;
import java.util.UUID;

public interface GameProfileService {

  GameProfile findGameProfileByGameId(UUID gameId);
}
