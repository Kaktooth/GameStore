package com.store.gamestore.persistence.repository.user.pictures;

import com.store.gamestore.persistence.entity.UserPicture;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserPictureRepository extends CommonRepository<UserPicture, UUID> {

  UserPicture findUserPictureByUserId(UUID userId);
}