package com.store.gamestore.controller.mvc;


import com.store.gamestore.model.dto.StoreBannerDTO;
import com.store.gamestore.model.util.StoreBannerMapper;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.StoreBanner;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.uploaded.UploadedGameService;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/banner")
@RequiredArgsConstructor
public class GameBannerController {

  private final UserHolder userHolder;
  private final CommonService<StoreBanner, UUID> bannerService;
  private final UploadedGameService uploadedGameService;
  private final StoreBannerMapper storeBannerMapper;

  @GetMapping("/upload")
  public String uploadBannerPage(Model model) {

    var user = userHolder.getAuthenticated();
    var uploadedGames = uploadedGameService.findAllByUserId(user.getId());
    model.addAttribute("user", user);
    model.addAttribute("gameBanner", new StoreBannerDTO());
    model.addAttribute("uploadedGames", uploadedGames);
    return "banner-upload";
  }

  @PostMapping("/upload")
  public String uploadBanner(@ModelAttribute StoreBannerDTO gameBanner) throws IOException {
    var user = userHolder.getAuthenticated();
    gameBanner.setUserId(user.getId());
    bannerService.save(storeBannerMapper.destinationToSource(gameBanner));

    return "redirect:/store";
  }
}
