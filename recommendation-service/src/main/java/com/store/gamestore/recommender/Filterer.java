package com.store.gamestore.recommender;

public interface Filterer<T> {

  Filterer<T> applyFilter(Filter<T> filter);

  T collect();
}
