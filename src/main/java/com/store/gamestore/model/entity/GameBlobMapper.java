package com.store.gamestore.model.entity;

import org.springframework.jdbc.core.RowMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameBlobMapper implements RowMapper<GameBlob> {
    public GameBlob mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameBlob gameBlob = new GameBlob();
        gameBlob.setVersion(rs.getString("version"));
        gameBlob.setName(rs.getString("file_name"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while (rs.next()) {
            try {
                outputStream.write(rs.getBytes("data"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        gameBlob.setBytes(outputStream.toByteArray());

        return gameBlob;
    }
}