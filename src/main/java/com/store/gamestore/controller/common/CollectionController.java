package com.store.gamestore.controller.common;

import com.store.gamestore.model.GameImage;
import com.store.gamestore.model.PictureType;
import com.store.gamestore.model.User;
import com.store.gamestore.model.UserGame;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.user.UserDetailsService;
import com.store.gamestore.util.GamePicturesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/collection")
public class CollectionController {

    private final CommonService<User, UUID> userService;
    private final CommonService<GameImage, UUID> gameImageService;
    private final CommonService<UserGame, UUID> userGamesRepository;

    @Autowired
    public CollectionController(CommonService<User, UUID> userService,
                                CommonService<GameImage, UUID> gameImageService,
                                CommonService<UserGame, UUID> userGamesRepository) {
        this.userService = userService;
        this.gameImageService = gameImageService;
        this.userGamesRepository = userGamesRepository;
    }

    @GetMapping
    public String getCollectionPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);

        List<UserGame> collection = userGamesRepository.getAll(user.getId());
        model.addAttribute("collection", collection);
        model.addAttribute("user", user);

        List<GameImage> gameCollectionImages = new ArrayList<>();
        for (var game : collection) {
            List<GameImage> gameImages = gameImageService.getAll(game.getGame().getId());
            GameImage gameImage = GamePicturesUtil.getGamePicture(gameImages,
                PictureType.COLLECTION);
            gameCollectionImages.add(gameImage);
        }
        model.addAttribute("gameCollectionImages", gameCollectionImages);

        return "collection";
    }
}
