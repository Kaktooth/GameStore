package com.store.gamestore.repository.user;

import com.store.gamestore.model.User;

public interface UserDetailsRepository {
    User get(String username);
}
