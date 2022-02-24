package com.store.gamestore.repository.user;

import com.store.gamestore.model.User;
import com.store.gamestore.repository.CommonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.UUID;

@Slf4j
@Repository
public class UserRepositoryImpl implements CommonRepository<User, UUID> {

    private final JdbcTemplate jdbcTemplate;

    private final String newUserQuery = "INSERT INTO users(username, password, enabled, email, phone_number) VALUES (?, ?, ?, ?, ?)";
    private final String queryForAuthorities = "INSERT INTO authorities(id, username, authority) VALUES (?, ?, ?)";
    private final String deleteUserQuery = "DELETE FROM users WHERE id = ?";
    private String query = "SELECT id, username, password, enabled, phone_number FROM users WHERE users.username = ?";

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(newUserQuery, new String[]{"id"});
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.getEnabled());
            ps.setString(4, user.getEmail());
            if(user.getPhone() != null){
                ps.setString(5, user.getPhone());
            }
            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(queryForAuthorities);
            ps.setInt(1, entityId);
            ps.setString(2, user.getEmail());
            ps.setInt(3, 0);
            return ps;
        });

//        if (entityId != null) {
//            return new User(entityId, user.getEmail(), user.getPassword(),
//                user.getPhone(), user.isEnabled());
//        } else {
//            throw new RuntimeException("User creation operation wasn't successful");
//        }
    }

    @Override
    public User get(UUID id) {
        return null;
    }

    @Override
    public void update(User object) {

    }

    @Override
    public void delete(UUID id) {

    }
}
