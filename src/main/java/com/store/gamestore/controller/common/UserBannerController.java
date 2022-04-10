package com.store.gamestore.controller.common;

import com.store.gamestore.model.entity.StoreBannerItem;
import com.store.gamestore.model.entity.StoreBannerItemDTO;
import com.store.gamestore.model.entity.UploadedGame;
import com.store.gamestore.model.entity.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/banners")
public class UserBannerController {
    private final CommonService<User, UUID> userService;
    private final CommonService<StoreBannerItem, UUID> bannerService;
    private final CommonService<UploadedGame, UUID> uploadedGameService;

    @Autowired
    public UserBannerController(CommonService<User, UUID> userService,
                                CommonService<StoreBannerItem, UUID> bannerService,
                                @Qualifier("uploadedGameService")
                                    CommonService<UploadedGame, UUID> uploadedGameService) {

        this.userService = userService;
        this.bannerService = bannerService;
        this.uploadedGameService = uploadedGameService;
    }

    @GetMapping("/upload")
    public String uploadBannerPage(Model model) {

        User user = getUser();
        List<UploadedGame> uploadedGames = uploadedGameService.getAll(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("gameBanner", new StoreBannerItemDTO());
        model.addAttribute("uploadedGames", uploadedGames);
        return "banner-upload";
    }

    @PostMapping("/upload")
    public String uploadBanner(@ModelAttribute StoreBannerItemDTO gameBanner,
                               Model model) throws IOException {
        User user = getUser();
        gameBanner.setUserId(user.getId());
        bannerService.save(new StoreBannerItem(gameBanner));

        return "redirect:/store";
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return ((UserDetailsService) userService).get(name);
    }
}
