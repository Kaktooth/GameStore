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

public class StoreBannerItemMapper implements RowMapper<StoreBannerItem> {
    Set<GameFile> gameFiles = new HashSet<>();
    Set<Genre> genre = new HashSet<>();
    Map<UUID, StoreBannerItem> uploadMap = new HashMap<>();

    public StoreBannerItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID gameId = (UUID) rs.getObject("game_id");
        StoreBannerItem storeBannerItem = uploadMap.get(gameId);
        if (storeBannerItem == null) {
            gameFiles = new HashSet<>();
            genre = new HashSet<>();
            storeBannerItem = new StoreBannerItem();
            Game game = new Game();
            game.setId(gameId);
            storeBannerItem.setGame(game);

            GameProfile gameProfile = new GameProfile();
            gameProfile.setId(rs.getInt("id"));
            gameProfile.setGameId(gameId);
            gameProfile.setPrice(rs.getBigDecimal("price"));
            gameProfile.setTitle(rs.getString("title"));
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
            storeBannerItem.setUser(user);
            uploadMap.put(gameId, storeBannerItem);
        }

        GameFile gameFile = new GameFile();
        gameFile.setGameId(gameId);
        gameFile.setObjectId(rs.getInt("object_id"));
        gameFile.setVersion(rs.getString("version"));
        gameFile.setId(rs.getInt("id"));
        gameFile.setName(rs.getString("file_name"));
        gameFiles.add(gameFile);
        storeBannerItem.getGame().setGameFiles(gameFiles);

        if (gameFiles.size() <= 1) {
            Genre newGenre = new Genre();
            newGenre.setId(rs.getInt("genre_id"));
            newGenre.setName(rs.getString("genre"));
            genre.add(newGenre);
            storeBannerItem.getGame().setGenre(new GameGenre(genre, gameId));
        }

        storeBannerItem.setDescription(rs.getString("banner_description"));
        return storeBannerItem;
    }
}