package com.store.gamestore.repository.game.genre;

import com.store.gamestore.model.entity.GameGenre;
import com.store.gamestore.model.entity.GameGenreMapper;
import com.store.gamestore.model.entity.Genre;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Slf4j
@Repository
public class GameGenreRepository extends AbstractRepository<GameGenre, UUID> {

    private static final String saveGameGenres = "INSERT INTO game_genres VALUES (?, ?)";
    private static final String getGameGenres = "SELECT * FROM game_genres " +
        "INNER JOIN genres gn ON game_genres.genre_id = gn.id " +
        "WHERE game_id = ?";

    private static final String deleteGameGenres = "DELETE FROM game_genres WHERE game_id = ?";

    public GameGenreRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GameGenre save(GameGenre genres) {
        for (Genre genre : genres.getGenres()) {
            jdbcTemplate.update(saveGameGenres, genres.getGameId(), genre.getId());
        }
        return genres;
    }

    @Override
    public GameGenre get(UUID gameId) {
        return jdbcTemplate.queryForObject(getGameGenres, new GameGenreMapper(), gameId);
    }

    @Override
    public void update(GameGenre genres) {
        delete(genres.getGameId());
        save(genres);
    }

    @Override
    public void delete(UUID gameId) {
        jdbcTemplate.update(deleteGameGenres, gameId);
    }
}
