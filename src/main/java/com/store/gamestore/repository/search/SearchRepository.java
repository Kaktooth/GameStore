package com.store.gamestore.repository.search;

import com.store.gamestore.model.entity.UploadedGame;
import com.store.gamestore.model.entity.UploadedGameMapper;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SearchRepository {

  protected final JdbcTemplate jdbcTemplate;

  private static final String searchTitle =
      "SELECT *\n"
          + "FROM uploaded_games\n"
          + "    INNER JOIN game_profiles gp on gp.game_id = uploaded_games.game_id\n"
          + "    INNER JOIN game_files gf ON uploaded_games.game_id = gf.game_id\n"
          + "    INNER JOIN users u ON uploaded_games.user_id = u.id\n"
          + "    INNER JOIN user_profiles up ON uploaded_games.user_id = up.user_id\n"
          + "    INNER JOIN system_requirements sr on gp.id = sr.game_profile_id\n"
          + "    INNER JOIN game_genres gg ON gf.game_id = gg.game_id\n"
          + "    INNER JOIN genres gn ON gn.id = gg.genre_id\n"
          + "WHERE to_tsvector('english', title || ' ' || gp.description) @@ to_tsquery('english', ?)\n"
          + "ORDER BY uploaded_games.game_id DESC";

  public List<UploadedGame> search(String title) {
    return new ArrayList<>(
        new LinkedHashSet<>(jdbcTemplate.query(searchTitle, new UploadedGameMapper(), title)));
  }
}