package com.store.gamestore.common.message.sender;

import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.entity.UserInteractionRemoval;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaUserInteractionSender implements UserInteractionSender {

  private final KafkaTemplate<String, UserInteraction> userInteractionKafkaTemplate;
  private final KafkaTemplate<String, UserInteractionRemoval> userInteractionRemovalKafkaTemplate;

  @Override
  public void send(InteractionType interactionType, UUID userId, UUID gameId) {
    var userInteraction = new UserInteraction(UUID.randomUUID().toString(), userId.toString(),
        gameId.toString(), interactionType, LocalDateTime.now(), false);
    userInteractionKafkaTemplate.send("user-interactions", userInteraction.getId(),
        userInteraction);
  }

  @Override
  public void sendRemoval(InteractionType interactionType, UUID userId, UUID gameId) {
    var userInteraction = new UserInteractionRemoval(userId.toString(), gameId.toString(),
        interactionType);
    userInteractionRemovalKafkaTemplate.send("user-interactions-removal",
        UUID.randomUUID().toString(), userInteraction);
  }
}
