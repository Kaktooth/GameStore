package com.store.gamestore.repository.enumeration.genre;

import com.store.gamestore.model.entity.Genre;
import com.store.gamestore.model.entity.GenreMapper;
import com.store.gamestore.repository.enumeration.AbstractEnumerationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

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
    public List<Genre> getAll() {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getGenres, new GenreMapper())));
    }
}
