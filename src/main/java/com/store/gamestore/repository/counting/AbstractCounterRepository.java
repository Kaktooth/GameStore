package com.store.gamestore.repository.counting;

import org.springframework.jdbc.core.JdbcTemplate;

public class AbstractCounterRepository<I> implements CounterRepository<I> {
    protected final JdbcTemplate jdbcTemplate;

    public AbstractCounterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void count(I id) {

    }

    @Override
    public void decreaseCount(I id) {

    }

    @Override
    public Integer getCount(I id) {
        return null;
    }
}
