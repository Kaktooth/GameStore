package com.store.gamestore.controller;

import com.store.gamestore.model.FavoriteGame;
import com.store.gamestore.model.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.user.UserDetailsService;
import com.store.gamestore.util.ImageUtil;
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
        model.addAttribute("user", user);
        model.addAttribute("favoriteGames", favoriteGames);

        return "profile";
    }

    @PostMapping("/edit-resume")
    public String changeResume(@RequestParam("resume") String resume,
                               Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);
        user.setResume(resume);
        userService.update(user);

        return "redirect:/profile";
    }

    @PostMapping("/edit-picture")
    public String changeResume(@RequestParam("picture") MultipartFile picture,
                               Model model) throws IOException {
        //TODO edit user picture
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);
        user.setImage(ImageUtil.createImageFromBytes(picture.getBytes()));
        userService.update(user);

        return "redirect:/profile";
    }
}
