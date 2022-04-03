package com.store.gamestore.model;

import com.store.gamestore.util.DateConverter;
import com.store.gamestore.util.ImageUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UserGameMapper implements RowMapper<UserGame> {
    Set<GameFile> gameFiles = new HashSet<>();
    Set<Genre> genre = new HashSet<>();
    Map<UUID, UserGame> uploadMap = new HashMap<>();

    public UserGame mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID gameId = (UUID) rs.getObject("game_id");
        UserGame userGame = uploadMap.get(gameId);
        if (userGame == null) {
            gameFiles = new HashSet<>();
            genre = new HashSet<>();
            userGame = new UserGame();
            Game game = new Game();
            game.setId(gameId);
            userGame.setGame(game);

            GameProfile gameProfile = new GameProfile();
            gameProfile.setId(rs.getInt("id"));
            gameProfile.setGameId(gameId);
            gameProfile.setPrice(rs.getBigDecimal("price"));
            gameProfile.setTitle(rs.getString("title"));
            gameProfile.setDeveloper(rs.getString("developer"));
            gameProfile.setPublisher(rs.getString("publisher"));
            gameProfile.setRating(rs.getInt("rating"));
            gameProfile.setViews(rs.getInt("views_count"));
            gameProfile.setPurchase(rs.getInt("purchase_count"));
            gameProfile.setFavorite(rs.getInt("favorite_count"));
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
            user.setPublicUsername(rs.getString("public_username"));
            user.setResume(rs.getString("resume"));
            user.setPhone(rs.getString("phone_number"));
            userGame.setUser(user);
            uploadMap.put(gameId, userGame);
        }

        GameFile gameFile = new GameFile();
        gameFile.setGameId(gameId);
        gameFile.setObjectId(rs.getInt("object_id"));
        gameFile.setVersion(rs.getString("version"));
        gameFile.setId(rs.getInt("id"));
        gameFile.setName(rs.getString("file_name"));
        gameFiles.add(gameFile);
        userGame.getGame().setGameFiles(gameFiles);

        if (gameFiles.size() <= 1) {
            Genre newGenre = new Genre();
            newGenre.setId(rs.getInt("genre_id"));
            newGenre.setName(rs.getString("genre"));
            genre.add(newGenre);
            userGame.getGame().setGenre(new GameGenre(genre, gameId));
        }

        return userGame;
    }
}