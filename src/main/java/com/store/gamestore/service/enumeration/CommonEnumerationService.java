package com.store.gamestore.service.enumeration;

import java.util.List;

public interface CommonEnumerationService<T, I> {
    T get(I id);

    I getId(String name);

    List<T> getAll();
}
