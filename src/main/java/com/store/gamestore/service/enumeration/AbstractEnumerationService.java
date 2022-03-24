package com.store.gamestore.service.enumeration;

import com.store.gamestore.repository.enumeration.CommonEnumerationRepository;

import java.util.Set;

public class AbstractEnumerationService<T, I> implements CommonEnumerationService<T, I> {

    protected final CommonEnumerationRepository<T, I> repository;

    public AbstractEnumerationService(CommonEnumerationRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public T get(I id) {
        return repository.get(id);
    }

    @Override
    public I getId(String name) {
        return repository.getId(name);
    }

    @Override
    public Set<T> getAll() {
        return repository.getAll();
    }
}
