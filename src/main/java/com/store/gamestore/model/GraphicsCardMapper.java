package com.store.gamestore.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GraphicsCardMapper implements RowMapper<GraphicsCard> {
    public GraphicsCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        GraphicsCard genre = new GraphicsCard();
        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("name"));
        return genre;
    }
}
