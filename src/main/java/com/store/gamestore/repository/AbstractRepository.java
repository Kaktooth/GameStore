package com.store.gamestore.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

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
    public List<T> getAll(I id) {
        return new ArrayList<>();
    }

    @Override
    public void update(T object) {

    }

    @Override
    public void delete(I id) {

    }
}
