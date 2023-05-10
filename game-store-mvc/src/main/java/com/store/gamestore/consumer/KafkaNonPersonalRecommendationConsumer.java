package com.store.gamestore.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.persistence.entity.GameInteraction;
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
public class KafkaNonPersonalRecommendationConsumer implements
    KafkaLatestRecordConsumer<List<GameInteraction>> {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<UUID, Object> redisTemplate;

  @KafkaListener(topics = {KafkaTopics.POPULAR_GAMES, KafkaTopics.MOST_PURCHASED_GAMES,
      KafkaTopics.FAVORITE_GAMES})
  void listenNonPersonalRecommendations(@Payload List<GameInteraction> recommendations,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) UUID key) {
    var typeReference = new TypeReference<List<GameInteraction>>() {
    };
    var gameRecommendations = objectMapper.convertValue(recommendations, typeReference);
    redisTemplate.opsForValue().set(key, gameRecommendations);
  }

  @Override
  public List<GameInteraction> getRecord(UUID key) {
    return (List<GameInteraction>) redisTemplate.opsForValue().get(key);
  }
}