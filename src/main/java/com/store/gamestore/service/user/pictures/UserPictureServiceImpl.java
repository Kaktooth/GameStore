package com.store.gamestore.service.user.pictures;

import com.store.gamestore.persistence.entity.UserPicture;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.user.pictures.UserPictureRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserPictureServiceImpl extends AbstractService<UserPicture, UUID> implements
    UserPictureService {

  public UserPictureServiceImpl(
      CommonRepository<UserPicture, UUID> repository) {
    super(repository);
  }

  @Override
  public UserPicture findUserPictureByUserId(UUID userId) {
    return ((UserPictureRepository) repository).findUserPictureByUserId(userId);
  }
}
