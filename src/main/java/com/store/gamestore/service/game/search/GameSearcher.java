package com.store.gamestore.service.game.search;

import java.util.List;

public interface GameSearcher<T> {
    List<T> searchGames(String searchString, Integer count);
}
