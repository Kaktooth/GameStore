package com.store.gamestore.model;


import com.store.gamestore.util.DateConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class UploadedGameMapper implements RowMapper<UploadedGame> {
    Set<GameFile> gameFiles = new HashSet<>();
    Set<Genre> genre = new HashSet<>();
    Map<UUID, UploadedGame> uploadMap = new HashMap<>();

    public UploadedGame mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID gameId = (UUID) rs.getObject("game_id");
        UploadedGame uploadedGame = uploadMap.get(gameId);
        if (uploadedGame == null) {
            genre = new HashSet<>();
            uploadedGame = new UploadedGame();
            Game game = new Game();
            game.setId(gameId);
            uploadedGame.setGame(game);
            GameFile gameFile = new GameFile();
            gameFile.setGameId(gameId);
            gameFile.setObjectId(rs.getInt("object_id"));
            gameFile.setVersion(rs.getString("version"));
            gameFile.setId(rs.getInt("id"));
            gameFile.setName(rs.getString("file_name"));
            gameFiles.add(gameFile);
            game.setGameFiles(gameFiles);

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

            User user = new User();
            user.setId((UUID) rs.getObject("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone_number"));
            uploadedGame.setUser(user);
            uploadMap.put(gameId, uploadedGame);
        }

        Genre newGenre = new Genre();
        newGenre.setId(rs.getInt("genre_id"));
        newGenre.setName(rs.getString("genre"));
        genre.add(newGenre);
        uploadedGame.getGame().setGenre(new GameGenre(genre, gameId));

        return uploadedGame;
    }
}
