package com.store.gamestore.controller;

import com.store.gamestore.model.FavoriteGame;
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

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private final CommonService<User, UUID> userService;
    private final CommonService<FavoriteGame, UUID> favoriteGameService;

    @Autowired
    public UserProfileController(CommonService<User, UUID> userService,
                                 CommonService<FavoriteGame, UUID> favoriteGameService) {
        this.userService = userService;
        this.favoriteGameService = favoriteGameService;
    }

    @GetMapping
    public String userProfilePage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);
        List<FavoriteGame> favoriteGames = favoriteGameService.getAll(user.getId());
        model.addAttribute("favoriteGames", favoriteGames);

        return "profile";
    }
}
