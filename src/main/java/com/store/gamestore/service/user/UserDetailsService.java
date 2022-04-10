package com.store.gamestore.service.user;

import com.store.gamestore.model.entity.User;

public interface UserDetailsService {
    User get(String username);
}
