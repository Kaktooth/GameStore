package com.store.gamestore.repository.game.file;

import com.store.gamestore.model.entity.GameBlob;
import com.store.gamestore.model.entity.GameBlobMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Repository
@Transactional
public class DownloadFilesRepository extends AbstractRepository<GameBlob, UUID> {

    private static final String getGameFileVersion = "SELECT MAX(CAST(REPLACE(version, '.', '') AS INTEGER)) FROM game_files WHERE game_id = ?;";

    private static final String getGameFile = "SELECT * FROM game_files " +
        "LEFT JOIN pg_largeobject lo ON loid = object_id " +
        "WHERE CAST(REPLACE(version, '.', '')AS INTEGER) = ?  AND game_id = ?;";

    @Autowired
    public DownloadFilesRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GameBlob get(UUID gameId) {
        Integer version = jdbcTemplate.queryForObject(getGameFileVersion, Integer.class, gameId);
        return jdbcTemplate.queryForObject(getGameFile, new GameBlobMapper(), version, gameId);
    }
}
