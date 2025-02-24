package com.store.gamestore.service.recommendation;

import com.store.gamestore.persistence.entity.GameRecommendation;
import java.util.List;
import java.util.UUID;

public interface GameRecommendationService {

  List<GameRecommendation> getRecommendations(UUID gameId);
}
