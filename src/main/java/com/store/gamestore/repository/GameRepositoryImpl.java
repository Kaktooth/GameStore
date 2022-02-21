package com.store.gamestore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.PreparedStatement;

@Repository
public class GameRepositoryImpl implements GameRepository<File, Integer> {

    private final JdbcTemplate jdbcTemplate;

    String saveGameToBlob = "SELECT lo_import(?);";

    String saveGameFile = "INSERT INTO games (name, object_id) VALUES (?, ?);";

    String getGameFile = "SELECT id, name, object_id FROM games";

    @Autowired
    public GameRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(File gameFile) {

        if (gameFile.length() < Runtime.getRuntime().freeMemory()) {
            try {
                Long oid = jdbcTemplate.queryForObject(saveGameToBlob, Long.class, gameFile.getAbsolutePath());
                jdbcTemplate.update(saveGameFile, gameFile.getName(), oid);
            } catch (RuntimeException exception){
                exception.printStackTrace();
            }
        }
    }

    @Override
    public File get(Integer id) {
        return null;
    }

    @Override
    public void update(File gameFile) {

    }

    @Override
    public void delete(Integer id) {

    }
}
