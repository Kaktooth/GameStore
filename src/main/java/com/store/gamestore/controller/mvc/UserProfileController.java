package com.store.gamestore.controller.mvc;

import com.store.gamestore.persistence.entity.FavoriteGame;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.Image;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.persistence.entity.UserPicture;
import com.store.gamestore.persistence.entity.UserProfile;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.favorite.FavoriteGameService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.user.UserService;
import com.store.gamestore.service.user.pictures.UserPictureService;
import com.store.gamestore.service.user.profile.UserProfileService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  private final CommonService<User, UUID> userService;
  private final CommonService<UserProfile, UUID> userProfileService;
  private final CommonService<GamePicture, UUID> gameImageService;
  private final FavoriteGameService favoriteGameService;
  private final CommonService<UserPicture, UUID> userImageService;
  private final CommonService<Image, UUID> imageService;

  @GetMapping
  public String userProfilePage(Model model) {

    User user = getUser();
    UserProfile userProfile = getUserProfile(user.getId());
    List<FavoriteGame> favoriteGames = favoriteGameService.findAllByUserId(user.getId());
    model.addAttribute("user", user);
    model.addAttribute("userProfile", userProfile);
    model.addAttribute("favoriteGames", favoriteGames);

    List<GamePicture> favoriteGamesImages = new ArrayList<>();
    for (var game : favoriteGames) {
      GamePicture gameImage = ((GamePictureService) gameImageService)
          .findGamePictureByGameIdAndPictureTypeId(game.getGame().getId(),
              GamePictureType.GAME_PAGE.ordinal());
      favoriteGamesImages.add(gameImage);
    }
    model.addAttribute("favoriteGamesImages", favoriteGamesImages);

    UserPicture userImage = ((UserPictureService) userImageService).findUserPictureByUserId(
        user.getId());
    model.addAttribute("userImage", userImage);

    return "profile";
  }

  @PostMapping("/edit-resume")
  public String changeResume(@RequestParam("resume") String resume) {
    User user = getUser();
    UserProfile userProfile = getUserProfile(user.getId());
    userProfile.setResume(resume);
    userProfileService.update(userProfile);

    return "redirect:/profile";
  }

  @PostMapping("/edit-picture")
  public String changePicture(@RequestParam("picture") MultipartFile picture) throws IOException {
    User user = getUser();
    UserPicture userImage = ((UserPictureService) userImageService).findUserPictureByUserId(
        user.getId());

    Image image = imageService.get(userImage.getImage().getId());
    image.setImageData(picture.getBytes());
    imageService.update(image);
    userImageService.update(userImage);

    return "redirect:/profile";
  }

  private User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    return ((UserService) userService).findUserByUsername(username);
  }

  private UserProfile getUserProfile(UUID userId) {
    return ((UserProfileService) userProfileService).getUserProfile(userId);
  }
}
