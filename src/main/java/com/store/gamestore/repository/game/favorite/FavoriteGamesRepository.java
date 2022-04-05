package com.store.gamestore.repository.game.favorite;

import com.store.gamestore.model.FavoriteGame;
import com.store.gamestore.model.FavoriteGameMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@Transactional
public class FavoriteGamesRepository extends AbstractRepository<FavoriteGame, UUID> {

    private static final String saveFavoriteGame = "INSERT INTO favorite_games VALUES (?, ?)";

    private static final String getFavoriteGame = "SELECT * FROM favorite_games " +
        "INNER JOIN game_files gf ON favorite_games.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON favorite_games.game_id = gp.game_id " +
        "INNER JOIN users u ON favorite_games.user_id = u.id " +
        "INNER JOIN user_profiles up ON favorite_games.user_id = up.user_id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE favorite_games.game_id = ?";

    private static final String getFavoriteGames = "SELECT * FROM favorite_games " +
        "INNER JOIN game_files gf ON favorite_games.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON favorite_games.game_id = gp.game_id " +
        "INNER JOIN users u ON favorite_games.user_id = u.id " +
        "INNER JOIN user_profiles up ON favorite_games.user_id = up.user_id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE favorite_games.user_id = ?";

    private static final String deleteFavorite = "DELETE FROM favorite_games WHERE user_id = ? AND game_id = ?";

    private static final String getUserId = "SELECT id FROM users WHERE username = ?";

    public FavoriteGamesRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public FavoriteGame save(FavoriteGame game) {
        jdbcTemplate.update(saveFavoriteGame, game.getUser().getId(), game.getGame().getId());
        return game;
    }

    @Override
    public FavoriteGame get(UUID gameId) {
        return jdbcTemplate.query(getFavoriteGame, new FavoriteGameMapper(), gameId).iterator().next();
    }

    @Override
    public List<FavoriteGame> getAll(UUID userId) {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getFavoriteGames, new FavoriteGameMapper(), userId)));
    }

    @Override
    public void delete(UUID gameId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        UUID userId = jdbcTemplate.queryForObject(getUserId, UUID.class, name);
        jdbcTemplate.update(deleteFavorite, userId, gameId);
    }
}
