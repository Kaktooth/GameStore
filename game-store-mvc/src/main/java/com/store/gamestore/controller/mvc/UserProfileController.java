package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.Image;
import com.store.gamestore.persistence.entity.UserPicture;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.favorite.FavoriteGameService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.user.pictures.UserPictureService;
import com.store.gamestore.service.user.profile.UserProfileService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

  private final UserHolder userHolder;
  private final UserProfileService userProfileService;
  private final CommonService<GamePicture, UUID> gameImageService;
  private final FavoriteGameService favoriteGameService;
  private final CommonService<UserPicture, UUID> userImageService;
  private final CommonService<Image, UUID> imageService;

  @GetMapping
  public String userProfilePage(Model model) {

    var user = userHolder.getAuthenticated();
    var userProfile = userProfileService.getUserProfile(user.getId());
    var favoriteGames = favoriteGameService.findAllByUserId(user.getId());
    model.addAttribute("user", user);
    model.addAttribute("userProfile", userProfile);
    model.addAttribute("favoriteGames", favoriteGames);

    var favoriteGamesImages = new ArrayList<>();
    for (var game : favoriteGames) {
      var gameImage = ((GamePictureService) gameImageService)
          .findGamePictureByGameIdAndPictureTypeId(game.getGame().getId(),
              GamePictureType.GAME_PAGE.ordinal());
      favoriteGamesImages.add(gameImage);
    }
    model.addAttribute("favoriteGamesImages", favoriteGamesImages);

    var userImage = ((UserPictureService) userImageService).findUserPictureByUserId(
        user.getId());
    model.addAttribute("userImage", userImage);

    return "profile";
  }

  @PostMapping("/edit-resume")
  public String changeResume(@RequestParam("resume") String resume) {

    var userProfile = userProfileService.getUserProfile(
        userHolder.getAuthenticated().getId());
    userProfile.setResume(resume);
    userProfileService.update(userProfile);

    return "redirect:/profile";
  }

  @PostMapping("/edit-picture")
  public String changePicture(@RequestParam("picture") MultipartFile picture) throws IOException {
    var userImage = ((UserPictureService) userImageService).findUserPictureByUserId(
        userHolder.getAuthenticated().getId());

    var image = imageService.get(userImage.getImage().getId());
    image.setImageData(picture.getBytes());
    imageService.update(image);
    userImageService.update(userImage);

    return "redirect:/profile";
  }
}
