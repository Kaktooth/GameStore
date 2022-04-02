package com.store.gamestore.repository.game.uploaded;

import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.model.UploadedGameMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class UploadedGamesRepository extends AbstractRepository<UploadedGame, UUID> {

    private static final String saveUploadedGame = "INSERT INTO uploaded_games VALUES (?, ?)";

    private static final String getGame = "SELECT * FROM uploaded_games " +
        "INNER JOIN game_files gf ON uploaded_games.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON uploaded_games.game_id = gp.game_id " +
        "INNER JOIN users u ON uploaded_games.user_id = u.id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE uploaded_games.game_id = ?";

    private static final String getUserUploadedGames = "SELECT * FROM uploaded_games " +
        "INNER JOIN game_files gf ON uploaded_games.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON uploaded_games.game_id = gp.game_id " +
        "INNER JOIN users u ON uploaded_games.user_id = u.id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE user_id = ?";

    private static final String getGames = "SELECT * FROM uploaded_games " +
        "INNER JOIN game_files gf ON uploaded_games.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON uploaded_games.game_id = gp.game_id " +
        "INNER JOIN users u ON uploaded_games.user_id = u.id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id ";

    public UploadedGamesRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public UploadedGame save(UploadedGame game) {
        jdbcTemplate.update(saveUploadedGame, game.getUser().getId(), game.getGame().getId());
        return game;
    }

    @Override
    public UploadedGame get(UUID gameId) {
        return jdbcTemplate.query(getGame, new UploadedGameMapper(), gameId).iterator().next();
    }

    @Override
    public List<UploadedGame> getAll(UUID userId) {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getUserUploadedGames, new UploadedGameMapper(), userId)));
    }

    @Override
    public List<UploadedGame> getAll() {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getGames, new UploadedGameMapper())));
    }

    @Override
    public void delete(UUID id) {

    }
}
