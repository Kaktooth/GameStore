package com.store.gamestore.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GameMapper implements RowMapper<Game> {
    Set<GameFile> gameFiles = new HashSet<>();

    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        UUID gameId = (UUID) rs.getObject("id");
        game.setId(gameId);

        if (gameId == null) {
            GameFile gameFile = new GameFile();
//            gameFile.setGameId(gameId);
            gameFile.setObjectId(rs.getInt("game_files.object_id"));
            gameFile.setVersion(rs.getString("game_files.version"));
            gameFile.setId(rs.getInt("game_files.id"));
            gameFile.setName(rs.getString("game_files.name"));
            gameFiles.add(gameFile);
        }
        game.setGameFiles(gameFiles);

        GameProfile gameProfile = new GameProfile();
        gameProfile.setGameId(gameId);
        gameProfile.setPrice(rs.getBigDecimal("game_profiles.id"));
        gameProfile.setName(rs.getString("game_profiles.name"));
        gameProfile.setDeveloper(rs.getString("game_profiles.developer"));
        gameProfile.setPublisher(rs.getString("game_profiles.publisher"));
        gameProfile.setRating(rs.getInt("game_profiles.rating"));
        gameProfile.setDescription(rs.getString("game_profiles.description"));
        gameProfile.setBriefDescription(rs.getString("game_profiles.brief_description"));
        gameProfile.setReleaseDate(LocalDateTime.parse(
            rs.getTimestamp("game_profiles.release_date").toString()
        ));
        game.setGameProfile(gameProfile);

        return game;
    }
}