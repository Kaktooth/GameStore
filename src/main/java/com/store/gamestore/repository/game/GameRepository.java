package com.store.gamestore.repository.game;

import com.store.gamestore.model.Game;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.PreparedStatement;

@Slf4j
@Repository
@Transactional
public class GameRepository extends AbstractRepository<Game, Integer> {

    private static final String saveGame = "INSERT INTO games (file_name, object_id) VALUES (?, ?);";

    private static final String getGame = "SELECT * FROM games WHERE id = ?";

    @Autowired
    public GameRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Game save(Game game) {
        if (game.getMultipartFile().getSize() < Runtime.getRuntime().freeMemory()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(saveGame, new String[]{"id"});
                ps.setString(1, game.getMultipartFile().getOriginalFilename());
                try {
                    ps.setBlob(2, game.getMultipartFile().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ps;
            }, keyHolder);

            Integer entityId = (Integer) keyHolder.getKey();
            if (entityId != null) {
                log.info("Game successfully saved");
            } else {
                log.info("Game wasn't successfully saved");
            }
            return get(entityId);
        }
        return null;
    }

    @Override
    public Game get(Integer gameId) {
        return jdbcTemplate.queryForObject(getGame, new BeanPropertyRowMapper<>(Game.class), gameId);
    }

    @Override
    public void update(Game gameFile) {

    }

    @Override
    public void delete(Integer gameId) {

    }
}
