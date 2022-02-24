package com.store.gamestore.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CommonRepository<T, I> {

    void save(T object);

    T get(I id);

    void update(T object);

    void delete(I id);
}
