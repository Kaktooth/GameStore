package com.store.gamestore.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public class AbstractRepository<T, I> implements CommonRepository<T, I> {

    protected final JdbcTemplate jdbcTemplate;

    public AbstractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(T object) {}

    @Override
    public T get(I id) {
        return null;
    }

    @Override
    public void update(T object) {

    }

    @Override
    public void delete(I id) {

    }
}
