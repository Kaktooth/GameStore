package com.store.gamestore.repository.game.favorite;

import com.store.gamestore.model.FavoriteGame;
import com.store.gamestore.model.FavoriteGameMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private static final String saveUploadedGame = "INSERT INTO favorite_games VALUES (?, ?)";

    private static final String getGame = "SELECT * FROM favorite_games " +
        "INNER JOIN game_files gf ON favorite_games.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON favorite_games.game_id = gp.game_id " +
        "INNER JOIN users u ON favorite_games.user_id = u.id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE favorite_games.game_id = ?";

    private static final String getGames = "SELECT * FROM favorite_games " +
        "INNER JOIN game_files gf ON favorite_games.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON favorite_games.game_id = gp.game_id " +
        "INNER JOIN users u ON favorite_games.user_id = u.id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE user_id = ?";

    public FavoriteGamesRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public FavoriteGame save(FavoriteGame game) {
        jdbcTemplate.update(saveUploadedGame, game.getUser().getId(), game.getGame().getId());
        return game;
    }

    @Override
    public FavoriteGame get(UUID gameId) {
        return jdbcTemplate.query(getGame, new FavoriteGameMapper(), gameId).iterator().next();
    }

    @Override
    public List<FavoriteGame> getAll(UUID userId) {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getGames, new FavoriteGameMapper(), userId)));
    }

    @Override
    public void delete(UUID id) {

    }
}
