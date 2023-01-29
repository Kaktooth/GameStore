package com.store.gamestore.controller.rest;

import com.store.gamestore.common.auth.LoginAuthenticationProvider;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class RestAuthController {

  private final UserService userService;
  private final LoginAuthenticationProvider loginAuthenticationProvider;

  @GetMapping
  public User getAuthenticatedUser(@RequestParam("username") String username,
      @RequestParam("password") String password) {
    var user = new User();
    var authentication = loginAuthenticationProvider.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));
    log.info("user authentication:" + username + ":" + password);

    if (authentication.isAuthenticated()) {
      user = userService.findUserByUsername(authentication.getName());
      user.setPassword("protected");

      log.info("user:" + user);
    }
    return user;
  }
}
