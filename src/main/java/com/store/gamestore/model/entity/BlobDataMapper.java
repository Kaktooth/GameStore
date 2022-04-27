package com.store.gamestore.model.entity;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlobDataMapper implements RowMapper<GameBlob> {

    LobHandler lobHandler = new DefaultLobHandler();

    public GameBlob mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameBlob blob = new GameBlob();
        String fileName = rs.getString("file_name");
        blob.setName(fileName);
        blob.setVersion(rs.getString("version"));
        blob.setName(fileName);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            os.write(lobHandler.getBlobAsBytes(rs, "data"));
            while (rs.next()) {
                byte[] bytes = rs.getBytes("data");
                os.write(bytes);

                System.out.println("bytes loaded: " + bytes.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        blob.setBytes(os.toByteArray());

        return blob;
    }
}
