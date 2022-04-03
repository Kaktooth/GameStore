package com.store.gamestore.controller;

import com.store.gamestore.model.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final CommonService<User, UUID> userService;

    @Autowired
    public AccountController(CommonService<User, UUID> userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAccountPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);
        System.out.println(user.toString());
        model.addAttribute("user", user);

        return "account";
    }
}
