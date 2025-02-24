package com.store.gamestore.service.search;

import java.util.List;

public interface SearchService<T> {
    List<T> searchGames(String searchString, Integer count);
}
