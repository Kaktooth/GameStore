package com.store.gamestore.service.user;

import com.store.gamestore.model.User;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService extends AbstractService<User, UUID> {

    public UserService(CommonRepository<User, UUID> userRepository) {
        super(userRepository);
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public User get(UUID id) {
        return repository.get(id);
    }

    @Override
    public void update(User user) {
        repository.update(user);
    }

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }
}
