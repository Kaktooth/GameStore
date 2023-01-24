package com.store.gamestore.model.util;

import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHolderImpl implements UserHolder {

  private final UserService userService;

  @Override
  public User getAuthenticated() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    var username = authentication.getName();
    return userService.findUserByUsername(username);
  }
}
