package com.store.gamestore.service.recommendation;

import com.store.gamestore.persistence.entity.Game;
import java.util.List;

public interface NonPersonalRecommendationService {

  List<Game> getPopularGames();

  List<Game> getMostPurchasedGames();

  List<Game> getFavoriteGames();
}
