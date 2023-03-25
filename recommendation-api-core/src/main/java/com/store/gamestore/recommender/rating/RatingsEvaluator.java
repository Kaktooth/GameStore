package com.store.gamestore.recommender.rating;

import com.store.gamestore.persistence.entity.GameRating;
import java.util.List;
import java.util.UUID;

public interface RatingsEvaluator {

  List<GameRating> getRatedGames(UUID userId);

  void evaluateRatings(UUID userId);
}
