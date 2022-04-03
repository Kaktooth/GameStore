package com.store.gamestore.controller;

import com.store.gamestore.model.GamePurchase;
import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.model.User;
import com.store.gamestore.model.UserGame;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    private final CommonService<User, UUID> userService;
    private final CommonService<UserGame, UUID> userGamesRepository;
    private final CommonService<UploadedGame, UUID> uploadedGameService;
    private final CommonService<GamePurchase, UUID> purchaseHistoryService;

    @Autowired
    public PurchaseController(CommonService<User, UUID> userService,
                              CommonService<UserGame, UUID> userGamesRepository,
                              @Qualifier("uploadedGameService")
                                  CommonService<UploadedGame, UUID> uploadedGameService,
                              CommonService<GamePurchase, UUID> purchaseHistoryService) {
        this.userService = userService;
        this.userGamesRepository = userGamesRepository;
        this.uploadedGameService = uploadedGameService;
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @GetMapping("/{id}")
    public String getPurchasePage(@PathVariable String id,
                                  Model model) {

        UploadedGame uploadedGame = uploadedGameService.get(UUID.fromString(id));
        model.addAttribute("uploadedGame", uploadedGame);
        return "purchase";
    }

    @PostMapping("/{id}")
    public String purchaseGame(@PathVariable String id,
                               Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);

        UploadedGame uploadedGame = uploadedGameService.get(UUID.fromString(id));
        UserGame userGame = new UserGame(user, uploadedGame.getGame());
        userGamesRepository.save(userGame);
        GamePurchase gamePurchase = new GamePurchase(uploadedGame.getGame().getGameProfile().getPrice(),
            LocalDateTime.now(), userGame);
        purchaseHistoryService.save(gamePurchase);

        return "redirect:/collection";
    }

    @GetMapping("/history")
    public String getPurchaseHistoryPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);

        List<GamePurchase> gamePurchaseList = purchaseHistoryService.getAll(user.getId());
        model.addAttribute("gamePurchaseList", gamePurchaseList);

        return "purchase-history";
    }

    @PostMapping("/{id}/get-free")
    public String getFreeGame(@PathVariable String id,
                              Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);

        UploadedGame uploadedGame = uploadedGameService.get(UUID.fromString(id));
        UserGame userGame = new UserGame(user, uploadedGame.getGame());
        userGamesRepository.save(userGame);

        return "redirect:/collection";
    }
}
