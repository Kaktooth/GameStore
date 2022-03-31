package com.store.gamestore.repository.game.profile;

import com.store.gamestore.model.GameProfile;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Slf4j
@Repository
public class GameProfileRepository extends AbstractRepository<GameProfile, Integer> {

    private static final String saveGameProfile = "INSERT INTO game_profiles(price, title," +
        " developer, publisher, rating, views_count, purchase_count, favorite_count, release_date," +
        " description, brief_description, game_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String getGameProfile = "SELECT * FROM game_profiles WHERE id = ?";
    private static final String updateGameProfile = "UPDATE game_profiles " +
        "SET price             = ?, title             = ?, " +
        "    developer         = ?, publisher         = ?, " +
        "    description       = ?, brief_description = ? " +
        "WHERE id = ?";
    private static final String deleteGameProfile = "DELETE FROM game_profiles WHERE id = ?";

    public GameProfileRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GameProfile save(GameProfile gameProfile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(saveGameProfile, new String[]{"id"});
            ps.setBigDecimal(1, gameProfile.getPrice());
            ps.setString(2, gameProfile.getTitle());
            ps.setString(3, gameProfile.getDeveloper());
            ps.setString(4, gameProfile.getPublisher());
            ps.setInt(5, gameProfile.getRating());
            ps.setInt(6, gameProfile.getRating());
            ps.setInt(7, gameProfile.getRating());
            ps.setInt(8, gameProfile.getRating());
            ps.setTimestamp(9, Timestamp.valueOf(gameProfile.getReleaseDate()));
            ps.setString(10, gameProfile.getDescription());
            ps.setString(11, gameProfile.getBriefDescription());
            ps.setObject(12, gameProfile.getGameId());
            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        return get(entityId);
    }

    @Override
    public GameProfile get(Integer id) {
        return jdbcTemplate.queryForObject(getGameProfile, new BeanPropertyRowMapper<>(GameProfile.class), id);
    }

    @Override
    public void update(GameProfile gameProfile) {
        log.info("game profile: " + gameProfile.getId());

        jdbcTemplate.update(updateGameProfile, gameProfile.getPrice(), gameProfile.getTitle(),
            gameProfile.getDeveloper(), gameProfile.getPublisher(), gameProfile.getDescription(),
            gameProfile.getBriefDescription(), gameProfile.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(deleteGameProfile, id);
    }
}
