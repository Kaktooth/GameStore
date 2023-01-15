package com.store.gamestore.persistence.repository.game;

import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends CommonRepository<Game, UUID> {

  @Query(nativeQuery = true, value = "SELECT * FROM games"
      + "         INNER JOIN game_profiles gp on gp.game_id = games.id\n"
      + "WHERE to_tsvector('english', title || ' ' || gp.brief_description) @@ to_tsquery('english', ?)")
  List<Game> fullSearchByGameTitle(String searchString);
}