package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.dto.PaymentInfoDTO;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.service.CommonService;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment-info/{gameId}")
@RequiredArgsConstructor
public class PaymentController {

  private final UserHolder userHolder;

  private final CommonService<Game, UUID> gameService;

  @GetMapping
  public String getPaymentInformationPage(@PathVariable UUID gameId, Model model) {
    var onlyLetters = "^[a-zA-Z\\s]+$";
    var onlyDigits = "^[\\d]+$";
    model.addAttribute("user", userHolder.getAuthenticated());
    model.addAttribute("paymentInfoInput", new PaymentInfoDTO());

    var game = gameService.get(gameId);
    model.addAttribute("game", game);
    model.addAttribute("paymentMethods", Map.of(0, "Visa", 1, "Mastercard"));
    model.addAttribute("currentYear", LocalDateTime.now().getYear());
    model.addAttribute("onlyLetters", onlyLetters);
    model.addAttribute("onlyDigits", onlyDigits);

    return "payment-info";
  }

  @PostMapping
  public String proceedVerifyingPaymentInfo(@ModelAttribute PaymentInfoDTO paymentInfo,
      @PathVariable UUID gameId) {

    verify();
    return "redirect:/purchase/" + gameId;
  }


  public void verify() {
    // TODO verify card
  }
}