package com.store.gamestore.service.game.profile;

import com.store.gamestore.persistence.entity.GameProfile;
import com.store.gamestore.service.CommonService;
import java.util.UUID;

public interface GameProfileService extends CommonService<GameProfile, UUID> {

  GameProfile findGameProfileByGameId(UUID gameId);
}
