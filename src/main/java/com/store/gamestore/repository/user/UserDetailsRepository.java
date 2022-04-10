package com.store.gamestore.repository.user;

import com.store.gamestore.model.entity.User;

public interface UserDetailsRepository {
    User get(String username);
}
