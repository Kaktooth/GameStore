package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.GameMetadata;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GameMetadataRepository extends CrudRepository<GameMetadata, UUID> {

  @Query(nativeQuery = true, value =
      "SELECT games.id, title, description, release_date FROM games\n"
          + "             JOIN game_profiles gp on games.id = gp.game_id")
  @NotNull
  List<GameMetadata> findAll();
}