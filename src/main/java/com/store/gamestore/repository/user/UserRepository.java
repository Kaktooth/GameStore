package com.store.gamestore.repository.user;

import com.store.gamestore.model.User;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.UUID;

@Slf4j
@Repository
public class UserRepository extends AbstractRepository<User, UUID> {

    private static final String insertAuthorities = "INSERT INTO user_authorities VALUES (0, 'USER'),   (1, 'ADMIN')";
    private static final String newUser = "INSERT INTO users(id, username, password, enabled, email) VALUES (?, ?, ?, ?, ?)";
    private static final String newUserProfile = "INSERT INTO user_profiles(id, public_username, user_id) VALUES (?, ?, ?)";
    private static final String newUsersAuthority = "INSERT INTO authorities(id, username, email, authority, user_id) VALUES (?, ?, ?, ?, ?)";
    private static final String deleteUser = "DELETE FROM users WHERE id = ?";
    private static final String query = "SELECT id, username, password, enabled, phone_number FROM users WHERE users.username = ?";

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void save(User user) {

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(newUser);
            ps.setObject(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.getEnabled());
            ps.setString(5, user.getEmail());
            return ps;
        });

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(newUsersAuthority);
            ps.setInt(1, 0);
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setInt(4, 0);
            ps.setObject(5, user.getId());
            return ps;
        });

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(newUserProfile);
            ps.setInt(1, 0);
            ps.setString(2, user.getProfileUsername());
            ps.setObject(3, user.getId());
            return ps;
        });

        log.info("User creation operation was successful");
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
