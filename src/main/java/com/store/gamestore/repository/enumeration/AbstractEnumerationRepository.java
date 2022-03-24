package com.store.gamestore.repository.enumeration;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.Set;

public class AbstractEnumerationRepository<T, I> implements CommonEnumerationRepository<T, I> {

    protected final JdbcTemplate jdbcTemplate;

    public AbstractEnumerationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public T get(I id) {
        return null;
    }

    @Override
    public I getId(String name) {
        return null;
    }

    @Override
    public Set<T> getAll() {
        return new HashSet<>();
    }
}
