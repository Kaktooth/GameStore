package com.store.gamestore.controller.common;

import com.store.gamestore.model.User;
import com.store.gamestore.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/create-account")
class UserController {

    private final AbstractService<User, UUID> userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(AbstractService<User, UUID> userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getRegisterPage() {
        return "create-account";
    }

    @PostMapping
    public String registerNewUser(@RequestParam(value = "user") String username,
                                  @RequestParam(value = "userProfile") String profileUsername,
                                  @RequestParam(value = "password") String password,
                                  @RequestParam(value = "email") String email) {

        String encodedPassword = passwordEncoder.encode(password);
        User user = User
            .builder()
            .id(UUID.randomUUID())
            .username(username)
            .publicUsername(profileUsername)
            .password(encodedPassword)
            .enabled(true)
            .email(email)
            .build();

       userService.save(user);

        return "redirect:/log-in";
    }
}
