package com.store.gamestore.service.game.recommended;

import java.util.List;

public interface Recommendations<T> {
    List<T> getPopularGames(Integer count);

    List<T> getBestSellerGames(Integer count);

    List<T> getMostFavoriteGames(Integer count);
}
