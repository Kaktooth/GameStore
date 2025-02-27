package com.store.gamestore.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.persistence.entity.GameRecommendation;
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
public class KafkaGameRecommendationConsumer implements
    KafkaLatestRecordConsumer<List<GameRecommendation>> {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<UUID, Object> redisTemplate;

  //TODO make only one consumer class for all topics
  @KafkaListener(topics = KafkaTopics.GAME_RECOMMENDATIONS)
  void listenGameRecommendations(@Payload List<GameRecommendation> recommendations,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) UUID key) {
    var typeReference = new TypeReference<List<GameRecommendation>>() {};
    var gameRecommendations = objectMapper.convertValue(recommendations, typeReference);
    redisTemplate.opsForValue().set(key, gameRecommendations);
  }

  @Override
  public List<GameRecommendation> getRecord(UUID key) {
    return (List<GameRecommendation>) redisTemplate.opsForValue().get(key);
  }
}
