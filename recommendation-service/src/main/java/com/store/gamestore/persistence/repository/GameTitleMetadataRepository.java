package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.GameTitleMetadata;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface GameTitleMetadataRepository extends CrudRepository<GameTitleMetadata, UUID> {

  GameTitleMetadata findByTitle(String title);
}
