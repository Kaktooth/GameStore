package com.store.gamestore.common.message.sender;

import com.store.gamestore.persistence.entity.InteractionType;
import java.util.UUID;

public interface UserInteractionSender {

  void send(InteractionType interactionType, UUID userId, UUID gameId);
  void sendRemoval(InteractionType interactionType, UUID userId, UUID gameId);
}
