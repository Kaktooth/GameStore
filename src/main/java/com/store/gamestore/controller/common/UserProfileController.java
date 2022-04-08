package com.store.gamestore.controller.common;

import com.store.gamestore.model.FavoriteGame;
import com.store.gamestore.model.GameImage;
import com.store.gamestore.model.PictureType;
import com.store.gamestore.model.User;
import com.store.gamestore.model.UserImage;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.user.UserDetailsService;
import com.store.gamestore.util.GamePicturesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private final CommonService<User, UUID> userService;
    private final CommonService<GameImage, UUID> gameImageService;
    private final CommonService<FavoriteGame, UUID> favoriteGameService;
    private final CommonService<UserImage, UUID> userImageService;

    @Autowired
    public UserProfileController(CommonService<User, UUID> userService,
                                 CommonService<GameImage, UUID> gameImageService,
                                 CommonService<FavoriteGame, UUID> favoriteGameService,
                                 CommonService<UserImage, UUID> userImageService) {
        this.userService = userService;
        this.gameImageService = gameImageService;
        this.favoriteGameService = favoriteGameService;
        this.userImageService = userImageService;
    }

    @GetMapping
    public String userProfilePage(Model model) {

        User user = getUser();
        List<FavoriteGame> favoriteGames = favoriteGameService.getAll(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("favoriteGames", favoriteGames);

        List<GameImage> favoriteGamesImages = new ArrayList<>();
        for (var game : favoriteGames) {
            List<GameImage> gameImages = gameImageService.getAll(game.getGame().getId());
            GameImage gameImage = GamePicturesUtil.getGamePicture(gameImages,
                PictureType.GAMEPAGE);
            favoriteGamesImages.add(gameImage);
        }
        model.addAttribute("favoriteGamesImages", favoriteGamesImages);

        UserImage userImage = userImageService.get(user.getId());
        model.addAttribute("userImage", userImage);

        return "profile";
    }

    @PostMapping("/edit-resume")
    public String changeResume(@RequestParam("resume") String resume,
                               Model model) {
        User user = getUser();
        user.setResume(resume);
        userService.update(user);

        return "redirect:/profile";
    }

    @PostMapping("/edit-picture")
    public String changePicture(@RequestParam("picture") MultipartFile picture,
                                Model model) throws IOException {

        User user = getUser();
        UserImage userImage = new UserImage(user.getId(), picture.getBytes());
        userImageService.update(userImage);

        return "redirect:/profile";
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return ((UserDetailsService) userService).get(name);
    }
}
