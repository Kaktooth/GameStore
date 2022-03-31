package com.store.gamestore.repository.store.banner;

import com.store.gamestore.model.StoreBannerItem;
import com.store.gamestore.model.StoreBannerItemMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class StoreBannerRepository extends AbstractRepository<StoreBannerItem, UUID> {

    private static final String saveUploadedGame = "INSERT INTO store_banner VALUES (?, ?)";

    private static final String getGame = "SELECT * FROM store_banner " +
        "INNER JOIN game_files gf ON store_banner.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON store_banner.game_id = gp.game_id " +
        "INNER JOIN users u ON store_banner.user_id = u.id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id " +
        "WHERE store_banner.game_id = ?";

    private static final String getGames = "SELECT * FROM store_banner " +
        "INNER JOIN game_files gf ON store_banner.game_id = gf.game_id " +
        "INNER JOIN game_profiles gp ON store_banner.game_id = gp.game_id " +
        "INNER JOIN users u ON store_banner.user_id = u.id " +
        "INNER JOIN system_requirements sr on gp.id = sr.game_profile_id " +
        "INNER JOIN game_genres gg ON gf.game_id = gg.game_id " +
        "INNER JOIN genres gn ON gn.id = gg.genre_id";

    public StoreBannerRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public StoreBannerItem save(StoreBannerItem game) {
        jdbcTemplate.update(saveUploadedGame, game.getUser().getId(), game.getGame().getId());
        return game;
    }

    @Override
    public StoreBannerItem get(UUID gameId) {
        return jdbcTemplate.query(getGame, new StoreBannerItemMapper(), gameId).iterator().next();
    }

    @Override
    public List<StoreBannerItem> getAll() {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getGames, new StoreBannerItemMapper())));
    }

    @Override
    public void delete(UUID id) {

    }
}
