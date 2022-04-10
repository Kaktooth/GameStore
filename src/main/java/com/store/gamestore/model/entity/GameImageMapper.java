package com.store.gamestore.model.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GameImageMapper implements RowMapper<GameImage> {
    public GameImage mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameImage gameImage = new GameImage();
        gameImage.setGameId((UUID) rs.getObject("game_id"));
        gameImage.setPictureType(rs.getString("game_picture_type"));
        gameImage.setImageData(rs.getBytes("image"));

        return gameImage;
    }
}
