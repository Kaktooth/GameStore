package com.store.gamestore.controller;

import com.store.gamestore.model.User;
import com.store.gamestore.model.UserGame;
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
@RequestMapping("/collection")
public class CollectionController {

    private final CommonService<User, UUID> userService;
    private final CommonService<UserGame, UUID> userGamesRepository;

    @Autowired
    public CollectionController(CommonService<User, UUID> userService,
                                CommonService<UserGame, UUID> userGamesRepository) {
        this.userService = userService;
        this.userGamesRepository = userGamesRepository;
    }

    @GetMapping
    public String getCollectionPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);

        List<UserGame> collection = userGamesRepository.getAll(user.getId());
        model.addAttribute("collection", collection);

        return "collection";
    }
}
