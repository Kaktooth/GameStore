package com.store.gamestore.model;

import com.store.gamestore.util.DateConverter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GamePurchaseMapper implements RowMapper<GamePurchase> {
    Set<GameFile> gameFiles = new HashSet<>();
    Set<Genre> genre = new HashSet<>();
    Map<UUID, GamePurchase> uploadMap = new HashMap<>();

    public GamePurchase mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID gameId = (UUID) rs.getObject("game_id");
        GamePurchase purchasedGame = uploadMap.get(gameId);
        if (purchasedGame == null) {
            gameFiles = new HashSet<>();
            genre = new HashSet<>();
            purchasedGame = new GamePurchase();
            UserGame userGame = new UserGame();
            Game game = new Game();
            game.setId(gameId);
            userGame.setGame(game);

            purchasedGame.setAmount(rs.getBigDecimal("amount"));
            purchasedGame.setDate(rs.getTimestamp("purchase_date").toLocalDateTime());

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
            user.setPhone(rs.getString("phone_number"));
            user.setPublicUsername(rs.getString("public_username"));
            user.setResume(rs.getString("resume"));
            userGame.setUser(user);
            purchasedGame.setUserGame(userGame);
            uploadMap.put(gameId, purchasedGame);
        }

        GameFile gameFile = new GameFile();
        gameFile.setGameId(gameId);
        gameFile.setObjectId(rs.getInt("object_id"));
        gameFile.setVersion(rs.getString("version"));
        gameFile.setId(rs.getInt("id"));
        gameFile.setName(rs.getString("file_name"));
        gameFiles.add(gameFile);
        purchasedGame.getUserGame().getGame().setGameFiles(gameFiles);

        if (gameFiles.size() <= 1) {
            Genre newGenre = new Genre();
            newGenre.setId(rs.getInt("genre_id"));
            newGenre.setName(rs.getString("genre"));
            genre.add(newGenre);
            purchasedGame.getUserGame().getGame().setGenre(new GameGenre(genre, gameId));
        }

        return purchasedGame;
    }
}
