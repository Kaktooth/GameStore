package com.store.gamestore.model.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StoreBannerItemMapper implements RowMapper<StoreBannerItem> {

    public StoreBannerItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        StoreBannerItem storeBannerItem = new StoreBannerItem();
        storeBannerItem.setGameId((UUID) rs.getObject("game_id"));
        storeBannerItem.setUserId((UUID) rs.getObject("user_id"));
        storeBannerItem.setImageData(rs.getBytes("image"));
        storeBannerItem.setDescription(rs.getString("banner_description"));
        return storeBannerItem;
    }
}