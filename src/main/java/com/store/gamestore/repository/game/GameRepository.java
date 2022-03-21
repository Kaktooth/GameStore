package com.store.gamestore.repository.game;

import com.store.gamestore.model.Game;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
public class GameRepository extends AbstractRepository<Game, Integer> {
    private static final String saveGame = "INSERT INTO games VALUES (?)";
    private static final String getGame = "SELECT * FROM games WHERE id = ?";

    public GameRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Game save(Game game) {
        jdbcTemplate.update(saveGame, game.getId());
        return game;
    }

    @Override
    public Game get(Integer gameId) {
        return jdbcTemplate.queryForObject(getGame, new BeanPropertyRowMapper<>(Game.class), gameId);
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void delete(Integer gameId) {

    }
}
