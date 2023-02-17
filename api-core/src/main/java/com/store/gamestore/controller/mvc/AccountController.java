package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.util.UserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

  private final UserHolder userHolder;

  @GetMapping
  public String getAccountPage(Model model) {
    model.addAttribute("user", userHolder.getAuthenticated());
    return "account";
  }
}
