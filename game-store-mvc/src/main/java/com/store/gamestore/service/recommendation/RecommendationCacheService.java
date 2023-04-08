package com.store.gamestore.service.recommendation;

import java.util.UUID;

public interface RecommendationCacheService<T> {

  void cache(T recommendations);

  T getCached(UUID key);
}
