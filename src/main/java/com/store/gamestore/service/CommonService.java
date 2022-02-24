package com.store.gamestore.service;

import org.springframework.stereotype.Service;

@Service
public interface CommonService<T, I> {

    void save(T object);

    T get(I id);

    void update(T object);

    void delete(I id);
}
