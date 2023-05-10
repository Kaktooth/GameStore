package com.store.gamestore.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("KafkaUserRecommendationConsumer")
public class KafkaUserRecommendationConsumer implements
    KafkaLatestRecordConsumer<List<UserRecommendation>> {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<UUID, Object> redisTemplate;

  @KafkaListener(topics = KafkaTopics.USER_RECOMMENDATIONS)
  void listenUserRecommendations(@Payload List<UserRecommendation> recommendations,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) UUID key) {
    var typeReference = new TypeReference<List<UserRecommendation>>() {};
    var gameRecommendations = objectMapper.convertValue(recommendations, typeReference);
    redisTemplate.opsForValue().set(key, gameRecommendations);
  }

  @Override
  public List<UserRecommendation> getRecord(UUID key) {
    return (List<UserRecommendation>) redisTemplate.opsForValue().get(key);
  }
}
