package com.store.gamestore.recommender.impl;

import com.store.gamestore.persistence.entity.GameRecommendation;
import com.store.gamestore.recommender.Filter;
import com.store.gamestore.recommender.Filterer;
import com.store.gamestore.recommender.GameRecommendationFilterer;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameRecommendationFiltererImpl implements GameRecommendationFilterer {

  private List<GameRecommendation> recommendations;

  @Override
  public Filterer<List<GameRecommendation>> applyFilter(
      Filter<List<GameRecommendation>> recommendationFilter) {
    recommendations = recommendationFilter.filter(recommendations);
    return this;
  }

  @Override
  public List<GameRecommendation> collect() {
    return recommendations;
  }
}
