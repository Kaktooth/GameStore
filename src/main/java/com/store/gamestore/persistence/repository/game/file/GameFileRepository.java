package com.store.gamestore.persistence.repository.game.file;

import com.store.gamestore.persistence.entity.GameFile;
import com.store.gamestore.persistence.repository.CommonRepository;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface GameFileRepository extends CommonRepository<GameFile, UUID> {

  List<GameFile> findAllByGameId(UUID gameId);

  @Query(nativeQuery = true, value = "SELECT MAX(CAST(REPLACE(version, '.', '') AS INTEGER)) "
      + "FROM game_files WHERE game_id = :#{#gameId}")
  Integer getLatestFileVersionByGameId(@Param("gameId") UUID gameId);

  @Query(nativeQuery = true, value = "SELECT * FROM game_files "
      + "WHERE game_id = :#{#gameId} AND CAST(REPLACE(version, '.', '')AS INTEGER) = :#{#fileVersion}")
  GameFile getLatestFile(@Param("gameId") UUID gameId, @Param("fileVersion") Integer fileVersion);
}