package com.store.gamestore.controller.mvc;

import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

  private final UserService userService;

  @GetMapping
  public String getAccountPage(Model model) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    User user = userService.findUserByUsername(name);

    model.addAttribute("user", user);
    return "account";
  }
}
