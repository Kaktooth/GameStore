package com.store.gamestore.service.user.profile;

import com.store.gamestore.persistence.entity.UserProfile;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.user.profile.UserProfileRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl extends AbstractService<UserProfile, UUID> implements
    UserProfileService {

  public UserProfileServiceImpl(
      CommonRepository<UserProfile, UUID> repository) {
    super(repository);
  }

  @Override
  public UserProfile getUserProfile(UUID userId) {
    return ((UserProfileRepository) repository).findUserProfileByUserId(userId);
  }
}
