package com.store.gamestore.repository.enumeration;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

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
    public List<T> getAll() {
        return new ArrayList<>();
    }
}
