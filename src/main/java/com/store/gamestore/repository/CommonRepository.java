package com.store.gamestore.repository;

public interface CommonRepository<T, I> {

    T save(T object);

    T get(I id);

    void update(T object);

    void delete(I id);
}
