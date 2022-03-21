package com.store.gamestore.repository.user;

import com.store.gamestore.model.User;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.UUID;

@Slf4j
@Repository
public class UserRepository extends AbstractRepository<User, UUID> {

    private static final String newUser = "INSERT INTO users(id, username, password, enabled, email) VALUES (?, ?, ?, ?, ?)";
    private static final String newUserWithPhone = "INSERT INTO users(id, username, password, enabled, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String newUserProfile = "INSERT INTO user_profiles(public_username, user_id) VALUES (?, ?)";
    private static final String newUsersAuthority = "INSERT INTO authorities(username, email, authority, user_id) VALUES (?, ?, ?, ?)";
    private static final String deleteUser = "DELETE FROM users WHERE id = ?";
    private static final String getUser = "SELECT users.id, username, password, enabled, email, phone_number, public_username FROM users, user_profiles WHERE users.id = :id";
    private static final String getUserWithPhone = "SELECT id, username, password, enabled, email, phone_number FROM users WHERE id = ?";

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public User save(User user) {
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(newUserWithPhone);
            ps.setObject(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.getEnabled());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhone());

            return ps;
        });

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(newUsersAuthority);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setInt(3, 0);
            ps.setObject(4, user.getId());
            return ps;
        });

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(newUserProfile);
            ps.setString(1, user.getProfileUsername());
            ps.setObject(2, user.getId());
            return ps;
        });

        log.info("User creation operation was successful");

        return user;
    }

    @Override
    public User get(UUID userId) {
        return jdbcTemplate.queryForObject(getUser, User.class, new MapSqlParameterSource().addValue("id", userId));
    }

    @Override
    public void update(User object) {

    }

    @Override
    public void delete(UUID userId) {

    }
}


