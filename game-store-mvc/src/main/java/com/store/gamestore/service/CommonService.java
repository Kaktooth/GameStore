package com.store.gamestore.service;

import java.util.List;

public interface CommonService<T, I> {

    T save(T object);

    T get(I id);

    List<T> getAll(Iterable<I> ids);

    List<T> getAll();

    void update(T object);

    void delete(I id);
}
