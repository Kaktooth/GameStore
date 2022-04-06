package com.store.gamestore.repository.counting;

public interface CounterRepository<I> {
    void count(I id);

    Integer getCount(I id);

    void decreaseCount(I id);
}
