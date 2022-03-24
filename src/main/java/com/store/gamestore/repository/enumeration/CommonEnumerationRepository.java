package com.store.gamestore.repository.enumeration;

import java.util.Set;

public interface CommonEnumerationRepository<T, I> {

    T get(I id);

    I getId(String name);

    Set<T> getAll();
}
