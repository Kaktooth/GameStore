package com.store.gamestore.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.Set;

public class AbstractRepository<T, I> implements CommonRepository<T, I> {

    protected final JdbcTemplate jdbcTemplate;

    public AbstractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public T save(T object) {
        return null;
    }

    @Override
    public T get(I id) {
        return null;
    }

    @Override
    public Set<T> getAll(I id) {
        return new HashSet<>();
    }

    @Override
    public void update(T object) {

    }

    @Override
    public void delete(I id) {

    }
}
