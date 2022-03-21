package com.store.gamestore.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComputerComponentMapper implements RowMapper<ComputerComponent> {
    public ComputerComponent mapRow(ResultSet rs, int rowNum) throws SQLException {
        ComputerComponent component = new ComputerComponent();
        component.setId(rs.getInt("id"));
        component.setName(rs.getString("name"));
        return component;
    }
}
