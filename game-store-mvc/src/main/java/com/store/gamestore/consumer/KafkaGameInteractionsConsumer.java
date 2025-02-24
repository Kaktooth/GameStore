package com.store.gamestore.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.persistence.entity.UserInteraction;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaGameInteractionsConsumer implements
    KafkaLatestRecordConsumer<HashMap<UUID, List<UserInteraction>>> {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<UUID, Object> redisTemplate;

  @KafkaListener(topics = KafkaTopics.USER_INTERACTION_METRICS)
  void listenInteractions(@Payload HashMap<UUID, List<UserInteraction>> gameInteractions,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) UUID key) {
    var typeReference = new TypeReference<HashMap<UUID, List<UserInteraction>>>() {};
    var metrics = objectMapper.convertValue(gameInteractions, typeReference);
    redisTemplate.opsForValue().set(key, metrics);
  }

  @Override
  public HashMap<UUID, List<UserInteraction>> getRecord(UUID key) {
    return (HashMap<UUID, List<UserInteraction>>) redisTemplate.opsForValue().get(key);
  }
}
