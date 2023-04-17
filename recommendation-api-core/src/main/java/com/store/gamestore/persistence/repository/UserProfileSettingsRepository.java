package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.UserProfileSettings;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileSettingsRepository extends MongoRepository<UserProfileSettings, UUID> {

}
