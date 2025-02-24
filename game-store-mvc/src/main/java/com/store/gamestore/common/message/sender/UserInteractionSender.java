package com.store.gamestore.common.message.sender;

import com.store.gamestore.persistence.entity.InteractionType;
import java.util.UUID;

public interface UserInteractionSender {

  void send(InteractionType interactionType, UUID userId, UUID gameId);

  void send(InteractionType interactionType, UUID userId, UUID gameId, Boolean recommended,
      String recommender);

  void sendRemoval(InteractionType interactionType, UUID userId, UUID gameId);
}
