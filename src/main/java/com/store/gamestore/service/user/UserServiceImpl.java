package com.store.gamestore.service.user;

import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.user.UserRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<User, UUID> implements UserService {

  public UserServiceImpl(CommonRepository<User, UUID> repository) {
    super(repository);
  }

  @Override
  public User findUserByUsername(String username) {
    return ((UserRepository) repository).findByUsername(username);
  }
}
