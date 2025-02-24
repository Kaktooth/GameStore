package com.store.gamestore.service.recommendation;

import com.store.gamestore.consumer.KafkaLatestRecordConsumer;
import com.store.gamestore.persistence.entity.GameRecommendation;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameRecommendationServiceImpl implements GameRecommendationService {

  private final KafkaLatestRecordConsumer<List<GameRecommendation>> kafkaRecommendationConsumer;

  @Override
  public List<GameRecommendation> getRecommendations(UUID gameId) {
    return kafkaRecommendationConsumer.getRecord(gameId);
  }
}