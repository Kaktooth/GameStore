package com.store.gamestore.service;

public interface CommonService<T, I> {

    T save(T object);

    T get(I id);

    void update(T object);

    void delete(I id);
}
