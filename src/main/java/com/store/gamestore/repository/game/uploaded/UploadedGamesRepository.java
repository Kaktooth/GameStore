package com.store.gamestore.repository.game.uploaded;

import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UploadedGamesRepository extends AbstractRepository<UploadedGame, Integer> {

    private static final String saveUploadedGame = "INSERT INTO uploaded_games VALUES (?, ?)";
    private static final String getGame = "SELECT * FROM uploaded_games WHERE game_id = ?";

    public UploadedGamesRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public UploadedGame save(UploadedGame game) {
        jdbcTemplate.update(saveUploadedGame, game.getUser().getId(), game.getGame().getId());
        return game;
    }

    @Override
    public UploadedGame get(Integer id) {
        return jdbcTemplate.queryForObject(getGame, new BeanPropertyRowMapper<>(UploadedGame.class), id);
    }

    @Override
    public void update(UploadedGame game) {

    }

    @Override
    public void delete(Integer id) {

    }
}
