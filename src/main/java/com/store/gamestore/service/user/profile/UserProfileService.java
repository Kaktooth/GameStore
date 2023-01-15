package com.store.gamestore.service.user.profile;

import com.store.gamestore.persistence.entity.UserProfile;
import java.util.UUID;

public interface UserProfileService {

  UserProfile getUserProfile(UUID userId);
}
