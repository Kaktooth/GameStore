package com.store.gamestore.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserImageMapper implements RowMapper<UserImage> {
    public UserImage mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserImage userImage = new UserImage();
        userImage.setUserId((UUID) rs.getObject("user_id"));
        userImage.setImageData(rs.getBytes("image"));

        return userImage;
    }
}