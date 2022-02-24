package com.store.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
//@RequestMapping("/sign-up")
//class UserController {
//
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @GetMapping
//    public String getRegisterPage() {
//        return "/sign-up";
//    }
//
//    @PostMapping
//    public String registerNewUser(@RequestParam(value = "user") String username,
//                                  @RequestParam(value = "password") String password,
//                                  @RequestParam(value = "phone") String phoneNumber) {
//
//        String encodedPassword = passwordEncoder.encode(password);
//        User user = new User(1, username, phoneNumber, encodedPassword, true);
//
//        userService.add(user);
//
//        return "/sign-in";
//    }
//}
