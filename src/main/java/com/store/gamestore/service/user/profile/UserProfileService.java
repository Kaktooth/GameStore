package com.store.gamestore.service.user.profile;

import com.store.gamestore.persistence.entity.UserProfile;
import com.store.gamestore.service.CommonService;
import java.util.UUID;

public interface UserProfileService extends CommonService<UserProfile, UUID> {

  UserProfile getUserProfile(UUID userId);
}
