package com.store.gamestore.service.user;

import com.store.gamestore.model.User;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.repository.user.UserDetailsRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService extends AbstractService<User, UUID> implements UserDetailsService{

    public UserService(CommonRepository<User, UUID> userRepository) {
        super(userRepository);
    }

    @Override
    public User get(String username) {
        return ((UserDetailsRepository)repository).get(username);
    }
}
