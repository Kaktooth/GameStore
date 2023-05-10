package com.store.gamestore.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.persistence.entity.CalculatedMetric;
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
public class KafkaGameMetricsConsumer implements
    KafkaLatestRecordConsumer<HashMap<UUID, List<CalculatedMetric>>> {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<UUID, Object> redisTemplate;

  @KafkaListener(topics = KafkaTopics.GAME_METRICS)
  void listenMetrics(@Payload HashMap<UUID, List<CalculatedMetric>> calculatedMetrics,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) UUID key) {
    var typeReference = new TypeReference<HashMap<UUID, List<CalculatedMetric>>>() {};
    var metrics = objectMapper.convertValue(calculatedMetrics, typeReference);
    redisTemplate.opsForValue().set(key, metrics);
  }

  @Override
  public HashMap<UUID, List<CalculatedMetric>> getRecord(UUID key) {
    return (HashMap<UUID, List<CalculatedMetric>>) redisTemplate.opsForValue().get(key);
  }
}
