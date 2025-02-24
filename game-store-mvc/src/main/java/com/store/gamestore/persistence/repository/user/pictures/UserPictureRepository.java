package com.store.gamestore.persistence.repository.user.pictures;

import com.store.gamestore.persistence.entity.UserPicture;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.UUID;

public interface UserPictureRepository extends CommonRepository<UserPicture, UUID> {

  UserPicture findUserPictureByUserId(UUID userId);
}