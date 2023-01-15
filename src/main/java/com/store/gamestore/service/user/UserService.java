package com.store.gamestore.service.user;

import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.service.CommonService;
import java.util.UUID;

public interface UserService extends CommonService<User, UUID> {

  User findUserByUsername(String username);
}
