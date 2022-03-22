package com.store.gamestore.controller;

import com.store.gamestore.model.Game;
import com.store.gamestore.model.UploadedGame;
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
@RequestMapping("/uploaded-games")
public class UploadedGamesController {

    private final CommonService<User, UUID> userService;
    private final CommonService<UploadedGame, UUID> gameService;

    @Autowired
    public UploadedGamesController(CommonService<UploadedGame, UUID> gameService,
                                   CommonService<User, UUID> userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping
    public String uploadedGamesPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);

        model.addAttribute("uploadedGames", gameService.getAll(user.getId()));

        return "uploaded-games";
    }
}
