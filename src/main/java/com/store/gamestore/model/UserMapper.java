package com.store.gamestore.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId((UUID) rs.getObject("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.setEmail(rs.getString("email"));
        user.setPublicUsername(rs.getString("public_username"));
//        user.setImage(ImageUtil.createImageFromBytes(rs.getBytes("image")));
        user.setResume(rs.getString("resume"));
        user.setPhone(rs.getString("phone_number"));
        return user;
    }
}

