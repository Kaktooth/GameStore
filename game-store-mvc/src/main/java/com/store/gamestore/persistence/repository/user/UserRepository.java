package com.store.gamestore.persistence.repository.user;

import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.UUID;

public interface UserRepository extends CommonRepository<User, UUID> {

  User findByUsername(String username);
}