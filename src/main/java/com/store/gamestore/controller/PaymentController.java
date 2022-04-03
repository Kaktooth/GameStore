package com.store.gamestore.controller;

import com.store.gamestore.model.PaymentInfoInput;
import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.model.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/payment-info/{id}")
public class PaymentController {
    private final CommonService<User, UUID> userService;
    private final CommonService<UploadedGame, UUID> uploadedGameService;

    @Autowired
    public PaymentController(CommonService<User, UUID> userService,
                             @Qualifier("uploadedGameService")
                                 CommonService<UploadedGame, UUID> uploadedGameService) {
        this.userService = userService;
        this.uploadedGameService = uploadedGameService;
    }


    @GetMapping
    public String getPaymentInformationPage(@PathVariable String id, Model model) {
        final String onlyLetters = "^[a-zA-Z\\s]+$";
        final String onlyDigits = "^[\\d]+$";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);
        model.addAttribute("user", user);

        model.addAttribute("paymentInfoInput", new PaymentInfoInput());
        UploadedGame uploadedGame = uploadedGameService.get(UUID.fromString(id));
        model.addAttribute("uploadedGame", uploadedGame);
        model.addAttribute("paymentMethods", Map.of(0, "Visa", 1, "Mastercard"));
        model.addAttribute("currentYear", LocalDateTime.now().getYear());
        model.addAttribute("onlyLetters", onlyLetters);
        model.addAttribute("onlyDigits", onlyDigits);

        return "payment-info";
    }

    @PostMapping
    public String proceedVerifyingPaymentInfo(@PathVariable String id,
                                              @ModelAttribute PaymentInfoInput paymentInfoInput,
                                              BindingResult bindingResult,
                                              Model model) {

        verify(id);
        return "redirect:/purchase/" + id;
    }


    public void verify(@PathVariable String id) {
        // TODO verify card
    }
}