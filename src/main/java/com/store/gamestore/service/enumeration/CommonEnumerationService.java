package com.store.gamestore.service.enumeration;

import java.util.Set;

public interface CommonEnumerationService<T, I> {
    T get(I id);

    I getId(String name);

    Set<T> getAll();
}
