package com.store.gamestore.repository.enumeration;

import java.util.List;

public interface CommonEnumerationRepository<T, I> {

    T get(I id);

    I getId(String name);

    List<T> getAll();
}
