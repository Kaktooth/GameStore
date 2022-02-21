package com.store.gamestore.repository;

public interface GameRepository<T, I> {

    void save(T object);

    T get(I id);

    void update(T object);

    void delete(I id);
}
