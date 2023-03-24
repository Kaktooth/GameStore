package com.store.gamestore.configuration;

import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.entity.UserInteractionRemoval;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

  private final UserInteractionRepository userInteractionRepository;

  @KafkaListener(id = "user-interactions", topics = "user-interactions", groupId = "user-interactions")
  public void onEvent(UserInteraction userInteraction) {
    if (userInteraction != null) {
      log.info("Received interaction: {}", userInteraction);
      userInteractionRepository.save(userInteraction);
    } else {
      log.error("Received null object");
    }
  }

  @KafkaListener(id = "user-interactions-removal", topics = "user-interactions-removal", groupId = "user-interactions-removal")
  public void onEvent(UserInteractionRemoval userInteraction) {
    if (userInteraction != null) {
      log.info("Received interaction removal: {}", userInteraction);
      userInteractionRepository.deleteAllByUserIdAndGameIdAndInteractionType(
          userInteraction.getUserId(), userInteraction.getGameId(),
          userInteraction.getInteractionType());
    } else {
      log.error("Received null object");
    }
  }
}

