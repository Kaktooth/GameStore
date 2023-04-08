package com.store.gamestore.recommender.impl;

import static com.store.gamestore.common.ApplicationConstants.RecommenderConstants.GAME_RECOMMENDATIONS_LIMIT;

import com.store.gamestore.persistence.entity.GameRecommendation;
import com.store.gamestore.recommender.GameRecommendationFilter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameRecommendationFilterImpl implements GameRecommendationFilter {

  @Override
  public List<GameRecommendation> filter(List<GameRecommendation> recommendations) {
    return recommendations.stream()
        .limit(GAME_RECOMMENDATIONS_LIMIT)
        .collect(Collectors.toList());
  }
}
