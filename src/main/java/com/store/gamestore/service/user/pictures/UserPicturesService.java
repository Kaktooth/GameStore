package com.store.gamestore.service.user.pictures;

import com.store.gamestore.model.UserImage;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserPicturesService extends AbstractService<UserImage, UUID> {
    public UserPicturesService(CommonRepository<UserImage, UUID> repository) {
        super(repository);
    }
}
