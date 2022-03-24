package com.store.gamestore.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GameGenreMapper implements RowMapper<GameGenre> {
    public GameGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameGenre gameGenre = new GameGenre();
        gameGenre.setGameId((UUID) rs.getObject("game_id"));

        Set<Genre> genreList = new HashSet<>();
        Genre genre = new Genre();
        genre.setId(rs.getInt("genre_id"));
        genre.setName(rs.getString("genre"));
        genreList.add(genre);
        gameGenre.setGenres(genreList);

        return gameGenre;
    }
}