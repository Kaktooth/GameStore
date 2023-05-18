package com.store.gamestore.recommender;

public interface Filter<T> {

  T filter(T items);
}
