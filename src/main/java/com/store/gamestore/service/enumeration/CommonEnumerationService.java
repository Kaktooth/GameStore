package com.store.gamestore.service.enumeration;

import java.util.List;

public interface CommonEnumerationService<T, I> {
    T get(I id);

    List<T> getAll();
}
