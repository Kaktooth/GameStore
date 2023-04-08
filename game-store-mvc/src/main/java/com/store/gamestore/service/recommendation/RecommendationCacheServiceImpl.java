package com.store.gamestore.service.recommendation;

import java.util.UUID;

public class RecommendationCacheServiceImpl<T> implements RecommendationCacheService<T> {

  @Override
  public void cache(T recommendations) {

  }

  @Override
  public T getCached(UUID key) {
    return null;
  }
}
