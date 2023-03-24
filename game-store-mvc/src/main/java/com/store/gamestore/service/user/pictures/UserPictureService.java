package com.store.gamestore.service.user.pictures;

import com.store.gamestore.persistence.entity.UserPicture;
import java.util.UUID;

public interface UserPictureService {

  UserPicture findUserPictureByUserId(UUID userId);
}
