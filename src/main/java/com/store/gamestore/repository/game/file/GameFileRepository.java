package com.store.gamestore.repository.game.file;

import com.store.gamestore.model.entity.GameFile;
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
public class GameFileRepository extends AbstractRepository<GameFile, Integer> {
    private static final String saveGameFile = "INSERT INTO game_files (file_name, object_id, version, game_id) VALUES (?, ?, ?, ?)";
    private static final String getGameFile = "SELECT * FROM game_files WHERE id = ?";
    private static final String deleteGameFile = "DELETE FROM game_files WHERE id = ?";

    @Autowired
    public GameFileRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GameFile save(GameFile gameFile) {
        if (gameFile.getMultipartFile().getSize() < Runtime.getRuntime().freeMemory()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(saveGameFile, new String[]{"id"});
                ps.setString(1, gameFile.getMultipartFile().getOriginalFilename());
                try {
                    ps.setBlob(2, gameFile.getMultipartFile().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ps.setString(3, gameFile.getVersion());
                ps.setObject(4, gameFile.getGameId());
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
    public GameFile get(Integer gameId) {
        return jdbcTemplate.queryForObject(getGameFile, new BeanPropertyRowMapper<>(GameFile.class), gameId);
    }

    @Override
    public void delete(Integer gameId) {
        jdbcTemplate.update(deleteGameFile, gameId);
    }
}
