package com.store.gamestore.persistence.repository.game.profile;

import com.store.gamestore.persistence.entity.GameProfile;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.UUID;

public interface GameProfileRepository extends CommonRepository<GameProfile, UUID> {
  GameProfile findGameProfileByGameId(UUID gameId);
}