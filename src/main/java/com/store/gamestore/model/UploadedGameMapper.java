package com.store.gamestore.model;


import com.store.gamestore.util.DateConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class UploadedGameMapper implements RowMapper<UploadedGame> {
    List<GameFile> gameFiles = new ArrayList<>();

    public UploadedGame mapRow(ResultSet rs, int rowNum) throws SQLException {
        UploadedGame uploadedGame = new UploadedGame();

        Game game = new Game();
        UUID gameId = (UUID) rs.getObject("game_id");
        game.setId(gameId);

        GameFile gameFile = new GameFile();
        gameFile.setGameId(gameId);
        gameFile.setObjectId(rs.getInt("object_id"));
        gameFile.setVersion(rs.getString("version"));
        gameFile.setId(rs.getInt("id"));
        gameFile.setName(rs.getString("file_name"));
        gameFiles.add(gameFile);

        game.setGameFile(gameFiles);

        GameProfile gameProfile = new GameProfile();
        gameProfile.setGameId(gameId);
        gameProfile.setPrice(rs.getBigDecimal("price"));
        gameProfile.setName(rs.getString("name"));
        gameProfile.setDeveloper(rs.getString("developer"));
        gameProfile.setPublisher(rs.getString("publisher"));
        gameProfile.setRating(rs.getInt("rating"));
        gameProfile.setDescription(rs.getString("description"));
        gameProfile.setBriefDescription(rs.getString("brief_description"));
        gameProfile.setReleaseDate(DateConverter.localDateFromTimestamp(
            rs.getTimestamp("release_date")
        ));
        game.setGameProfile(gameProfile);
        uploadedGame.setGame(game);

        User user = new User();
        user.setId((UUID) rs.getObject("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone_number"));
        uploadedGame.setUser(user);

        log.info(uploadedGame.toString());
        return uploadedGame;
    }
}
