package com.store.gamestore.model.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperatingSystemMapper implements RowMapper<OperatingSystem> {
    public OperatingSystem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OperatingSystem genre = new OperatingSystem();
        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("name"));
        return genre;
    }
}