package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.UserRecommenderProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserRecommenderProfile, String> {

}
