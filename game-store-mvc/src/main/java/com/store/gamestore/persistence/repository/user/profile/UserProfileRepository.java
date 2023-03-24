package com.store.gamestore.persistence.repository.user.profile;

import com.store.gamestore.persistence.entity.UserProfile;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CommonRepository<UserProfile, UUID> {

  UserProfile findUserProfileByUserId(UUID userId);
}