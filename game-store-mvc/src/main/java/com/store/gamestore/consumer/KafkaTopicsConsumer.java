package com.store.gamestore.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.gamestore.common.AppConstraints.KafkaTopics;
import java.util.Map;
import java.util.Set;
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
public class KafkaTopicsConsumer implements
    KafkaLatestRecordConsumer<Map<Integer, Set<String>>> {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<UUID, Object> redisTemplate;

  @KafkaListener(topics = KafkaTopics.TOPIC_VOCABULARY)
  void listenTopics(@Payload Map<Integer, Set<String>> recommendations,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) UUID key) {
    var typeReference = new TypeReference<Map<Integer, Set<String>>>() {
    };
    var gameRecommendations = objectMapper.convertValue(recommendations, typeReference);
    redisTemplate.opsForValue().set(key, gameRecommendations);
  }

  @Override
  public Map<Integer, Set<String>> getRecord(UUID key) {
    return (Map<Integer, Set<String>>) redisTemplate.opsForValue().get(key);
  }
}
