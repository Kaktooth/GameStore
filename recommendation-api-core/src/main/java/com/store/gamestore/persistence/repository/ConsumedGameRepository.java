package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.ConsumedGame;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ConsumedGameRepository extends CrudRepository<ConsumedGame, UUID> {

}
