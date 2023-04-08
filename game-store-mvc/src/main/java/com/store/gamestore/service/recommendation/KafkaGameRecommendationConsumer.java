package com.store.gamestore.service.recommendation;

import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.persistence.entity.GameRecommendation;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
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

  private final RedisTemplate<UUID, List<GameRecommendation>> redisTemplate;

  @KafkaListener(topics = KafkaTopics.GAME_RECOMMENDATIONS)
  void listenRecommendations(@Payload List<GameRecommendation> recommendations,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) UUID key) {
    redisTemplate.opsForValue().set(key, recommendations);
  }

  @Override
  public List<GameRecommendation> getRecord(String topic, UUID key) {
    return redisTemplate.opsForValue().get(key);
  }
}
