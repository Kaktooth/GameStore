package com.store.gamestore.repository.counting.views;

import com.store.gamestore.repository.counting.AbstractCounterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Slf4j
@Repository
@Qualifier("gameViewsCounterRepository")
public class GameViewsCounterRepository extends AbstractCounterRepository<UUID> {
    private static final String getCount = "SELECT favorite_count FROM game_profiles " +
        "WHERE game_profiles.game_id = ?";
    private static final String countViews = "UPDATE game_profiles " +
        "SET views_count = ? " +
        "WHERE game_id = ?";

    public GameViewsCounterRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Integer getCount(UUID id) {
        return jdbcTemplate.queryForObject(getCount, Integer.class, id);
    }

    @Override
    public void count(UUID id) {
        jdbcTemplate.update(countViews, getCount(id) + 1, id);
    }

}