package com.store.gamestore.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination<T> {

    private final List<T> games;

    public Pagination(List<T> games) {
        this.games = games;
    }

    public List<T> getCurrentPageContent(int page, int size) {
        List<T> gamesSet = games;
        gamesSet = gamesSet.stream()
            .skip((long) (page - 1) * size)
            .limit(size)
            .collect(Collectors.toList());

        return gamesSet;
    }

    public List<Integer> getPageNumbers(int pageCount) {
        return IntStream.rangeClosed(1, pageCount + 1)
            .boxed()
            .collect(Collectors.toList());
    }

    public int getPageCount(int pageSize) {
        return (games.size() - 1) / pageSize;
    }

    public Map<Integer, List<T>> toMap(Integer size, Integer pages) {
        Map<Integer, List<T>> bestSellerGamesMap = new HashMap<>();
        for (Integer page : getPageNumbers(pages)) {
            bestSellerGamesMap.put(page, getCurrentPageContent(page, size));
        }
        return bestSellerGamesMap;
    }
}
