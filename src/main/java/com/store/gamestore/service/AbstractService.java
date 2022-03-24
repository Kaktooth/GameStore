package com.store.gamestore.service;

import com.store.gamestore.repository.CommonRepository;

import java.util.Set;

public class AbstractService<T, I> implements CommonService<T, I> {

    protected final CommonRepository<T, I> repository;

    public AbstractService(CommonRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T object) {
        return repository.save(object);
    }

    @Override
    public T get(I id) {
        return repository.get(id);
    }

    @Override
    public Set<T> getAll(I id) {
        return repository.getAll(id);
    }

    @Override
    public void update(T object) {
        repository.update(object);
    }

    @Override
    public void delete(I id) {
        repository.delete(id);
    }
}
