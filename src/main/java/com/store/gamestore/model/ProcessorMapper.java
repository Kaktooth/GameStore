package com.store.gamestore.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcessorMapper implements RowMapper<Processor> {
    public Processor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Processor genre = new Processor();
        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("name"));
        return genre;
    }
}