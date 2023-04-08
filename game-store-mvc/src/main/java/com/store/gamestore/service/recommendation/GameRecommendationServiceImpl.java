package com.store.gamestore.service.recommendation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.gamestore.common.AppConstraints.KafkaTopics;
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

  private final KafkaLatestRecordConsumer<List<GameRecommendation>> kafkaLatestRecordConsumer;

  private final ObjectMapper objectMapper;

  @Override
  public List<GameRecommendation> getRecommendations(UUID gameId) {
    var gameRecommendations = kafkaLatestRecordConsumer.getRecord(KafkaTopics.GAME_RECOMMENDATIONS,
        gameId);
    var recommendations = objectMapper.convertValue(gameRecommendations,
        new TypeReference<List<GameRecommendation>>() {});
    return recommendations;
  }
}