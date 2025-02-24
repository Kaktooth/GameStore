package com.store.gamestore.model.util;

import com.store.gamestore.persistence.entity.User;

public interface UserHolder {

  User getAuthenticated();
}
