package com.store.gamestore.repository.game;

import com.store.gamestore.repository.CommonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;

@Slf4j
@Repository
public class GameRepositoryImpl implements CommonRepository<MultipartFile, Integer> {

    private final JdbcTemplate jdbcTemplate;

    String saveGameToBlob = "SELECT lo_import(?);";

    String saveGameFile = "INSERT INTO games (file_name, large_object) VALUES (?, ?);";

    String getGameFile = "SELECT id, name, object_id FROM games";

    @Autowired
    public GameRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(MultipartFile gameFile) {

        if (gameFile.getSize() < Runtime.getRuntime().freeMemory()) {
                jdbcTemplate.update(conn -> {
                    PreparedStatement ps = conn.prepareStatement(saveGameFile, new String[]{"id"});
                    try {
                        ps.setString(1, gameFile.getOriginalFilename());
                        ps.setBlob(2, gameFile.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return ps;
                });
        }
    }

    @Override
    public MultipartFile get(Integer id) {
        return null;
    }

    @Override
    public void update(MultipartFile gameFile) {

    }

    @Override
    public void delete(Integer id) {

    }
}
