package com.store.gamestore.repository.counting.favorite;

import com.store.gamestore.repository.counting.AbstractCounterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Slf4j
@Repository
@Qualifier("gameFavoriteCounterRepository")
public class GameFavoriteCounterRepository extends AbstractCounterRepository<UUID> {
    private static final String getCount = "SELECT game_profiles.favorite_count FROM game_profiles " +
        "WHERE game_profiles.game_id = ?";

    private static final String countFavorite = "UPDATE game_profiles " +
        "SET favorite_count = ? " +
        "WHERE game_id = ?";

    public GameFavoriteCounterRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Integer getCount(UUID id) {
        return jdbcTemplate.queryForObject(getCount, Integer.class, id);
    }

    @Override
    public void count(UUID id) {
        jdbcTemplate.update(countFavorite, getCount(id) + 1, id);
    }

    @Override
    public void decreaseCount(UUID id) {
        jdbcTemplate.update(countFavorite, getCount(id) - 1, id);
    }
}