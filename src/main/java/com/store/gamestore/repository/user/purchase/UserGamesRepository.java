package com.store.gamestore.repository.user.purchase;

import com.store.gamestore.model.entity.UserGame;
import com.store.gamestore.model.entity.UserGameMapper;
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
public class UserGamesRepository extends AbstractRepository<UserGame, UUID> {

    private static final String savePurchasedGame = "INSERT INTO user_games(user_id, game_id) VALUES (?, ?)";

    private static final String getPurchasedGames = "SELECT * FROM user_games " +
        "INNER JOIN game_profiles gp ON user_games.game_id = gp.game_id " +
        "INNER JOIN game_files gf ON user_games.game_id = gf.game_id " +
        "INNER JOIN users u ON user_games.user_id = u.id " +
        "INNER JOIN user_profiles up ON user_games.user_id = up.user_id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE user_games.user_id = ?";


    public UserGamesRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public UserGame save(UserGame game) {
        jdbcTemplate.update(savePurchasedGame, game.getUser().getId(), game.getGame().getId());

        return game;
    }

    @Override
    public List<UserGame> getAll(UUID id) {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getPurchasedGames, new UserGameMapper(), id)));
    }
}
