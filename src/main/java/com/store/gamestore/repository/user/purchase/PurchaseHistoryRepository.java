package com.store.gamestore.repository.user.purchase;

import com.store.gamestore.model.GamePurchase;
import com.store.gamestore.model.GamePurchaseMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class PurchaseHistoryRepository extends AbstractRepository<GamePurchase, UUID> {

    private static final String savePurchaseHistory = "INSERT INTO purchase_history VALUES (?, ?, ?, ?)";

    private static final String getPurchasedGame = "SELECT * FROM user_games " +
        "INNER JOIN game_files gf ON user_games.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON user_games.game_id = gp.game_id " +
        "INNER JOIN purchase_history ph ON user_games.game_id = ph.game_id AND user_games.user_id = ph.user_id " +
        "INNER JOIN users u ON user_games.user_id = u.id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE user_games.game_id = ?";

    private static final String getPurchasedGames = "SELECT * FROM user_games " +
        "INNER JOIN game_files gf ON user_games.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON user_games.game_id = gp.game_id " +
        "INNER JOIN purchase_history ph ON user_games.game_id = ph.game_id AND user_games.user_id = ph.user_id " +
        "INNER JOIN users u ON user_games.user_id = u.id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE user_games.user_id = ?";


    public PurchaseHistoryRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GamePurchase save(GamePurchase gamePurchase) {
        jdbcTemplate.update(savePurchaseHistory, gamePurchase.getAmount(), gamePurchase.getDate(),
            gamePurchase.getUserGame().getUser().getId(), gamePurchase.getUserGame().getGame().getId());

        return gamePurchase;
    }

    @Override
    public GamePurchase get(UUID gameId) {
        return jdbcTemplate.queryForObject(getPurchasedGame, new BeanPropertyRowMapper<>(GamePurchase.class), gameId);
    }

    @Override
    public List<GamePurchase> getAll(UUID id) {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getPurchasedGames, new GamePurchaseMapper(), id)));
    }
}
