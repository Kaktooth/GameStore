package com.store.gamestore.service.counting;

public interface CounterService<I> {
    void count(I id);

    Integer getCount(I id);

    void decreaseCount(I id);
}
