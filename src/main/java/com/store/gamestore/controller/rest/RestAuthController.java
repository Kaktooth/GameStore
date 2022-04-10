package com.store.gamestore.controller.rest;

import com.store.gamestore.auth.LoginAuthenticationProvider;
import com.store.gamestore.model.entity.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.user.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class RestAuthController {
    private final CommonService<User, UUID> userService;
    private final LoginAuthenticationProvider provider;

    @Autowired
    public RestAuthController(CommonService<User, UUID> userService, LoginAuthenticationProvider provider) {
        this.userService = userService;
        this.provider = provider;
    }


    @GetMapping
    public User getAuthenticatedUser(@RequestParam("username") String username,
                                     @RequestParam("password") String password) {
        User user = new User();
        Authentication authentication = provider.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
        log.info("user authentication:" + username + ":" + password);

        if (authentication.isAuthenticated()) {
            user = ((UserDetailsService) userService).get(authentication.getName());
            user.setPassword("protected");

            log.info("user:" + user);
        }
        return user;
    }
}
