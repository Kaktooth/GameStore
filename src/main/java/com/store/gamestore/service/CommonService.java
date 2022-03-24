package com.store.gamestore.service;

import java.util.Set;

public interface CommonService<T, I> {

    T save(T object);

    T get(I id);

    Set<T> getAll(I id);

    void update(T object);

    void delete(I id);
}
