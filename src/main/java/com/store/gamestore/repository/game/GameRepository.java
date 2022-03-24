package com.store.gamestore.repository.game;

import com.store.gamestore.model.Game;
import com.store.gamestore.model.GameMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Repository
public class GameRepository extends AbstractRepository<Game, UUID> {
    private static final String saveGame = "INSERT INTO games VALUES (?)";
    private static final String getGame = "SELECT * FROM games " +
        "INNER JOIN game_files gf ON games.id = gf.game_id " +
        "INNER JOIN game_profiles gp ON games.id = gp.game_id " +
        "WHERE games.id = ?";


    public GameRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Game save(Game game) {
        jdbcTemplate.update(saveGame, game.getId());
        return game;
    }

    @Override
    public Game get(UUID gameId) {
        return jdbcTemplate.queryForObject(getGame, new BeanPropertyRowMapper<>(Game.class), gameId);
    }

    @Override
    public Set<Game> getAll(UUID id) {
        return new HashSet<>(jdbcTemplate.query(getGame,  new GameMapper(), id));
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void delete(UUID gameId) {

    }
}
