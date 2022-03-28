package com.store.gamestore.repository.enumeration.genre;

import com.store.gamestore.model.Genre;
import com.store.gamestore.model.GenreMapper;
import com.store.gamestore.repository.enumeration.AbstractEnumerationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Repository
public class GenreRepository extends AbstractEnumerationRepository<Genre, Integer> {
    private static final String getGenre = "SELECT * FROM genres WHERE id = ?";
    private static final String getGenres = "SELECT * FROM genres ORDER BY genre";

    public GenreRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Genre get(Integer id) {
        return jdbcTemplate.queryForObject(getGenre, new BeanPropertyRowMapper<>(Genre.class), id);
    }

    @Override
    public Set<Genre> getAll() {
        return new HashSet<>(jdbcTemplate.query(getGenres, new GenreMapper()));
    }
}
