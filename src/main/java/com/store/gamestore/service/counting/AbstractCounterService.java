package com.store.gamestore.service.counting;

import com.store.gamestore.repository.counting.CounterRepository;

public class AbstractCounterService<I> implements CounterService<I> {
    protected final CounterRepository<I> repository;

    public AbstractCounterService(CounterRepository<I> repository) {
        this.repository = repository;
    }

    @Override
    public void count(I id) {
        repository.count(id);
    }

    @Override
    public Integer getCount(I id) {
        return repository.getCount(id);
    }

    @Override
    public void decreaseCount(I id) {
        repository.decreaseCount(id);
    }
}
