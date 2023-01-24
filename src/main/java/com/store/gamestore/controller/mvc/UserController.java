package com.store.gamestore.controller.mvc;


import com.store.gamestore.persistence.entity.Authorities;
import com.store.gamestore.persistence.entity.Authority;
import com.store.gamestore.persistence.entity.Image;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.persistence.entity.UserPicture;
import com.store.gamestore.persistence.entity.UserProfile;
import com.store.gamestore.service.AbstractService;
import com.store.gamestore.service.user.UserService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/create-account")
@AllArgsConstructor
class UserController {

  private static final byte[] EMPTY_PICTURE = new byte[0];

  private final UserService userService;
  private final AbstractService<UserProfile, UUID> userProfileService;
  private final AbstractService<Authority, UUID> authorityService;
  private final AbstractService<UserPicture, UUID> userPictureService;
  private final AbstractService<Image, UUID> imageService;
  private final PasswordEncoder passwordEncoder;

  @GetMapping
  public String getRegisterPage() {
    return "create-account";
  }

  @PostMapping
  public String registerNewUser(@RequestParam(value = "user") String username,
      @RequestParam(value = "userProfile") String publicUsername,
      @RequestParam(value = "password") String password,
      @RequestParam(value = "email") String email) {

    var encodedPassword = passwordEncoder.encode(password);
    var user = User
        .builder()
        .username(username)
        .password(encodedPassword)
        .enabled(true)
        .email(email)
        .publicUsername(publicUsername)
        .build();

    var createdUser = userService.save(user);
    var authority = new Authority(username, email, Authorities.USER.ordinal(),
        createdUser.getId());
    var userProfile = new UserProfile(createdUser.getId());
    var userPicture = new UserPicture(createdUser.getId(), new Image(EMPTY_PICTURE));
    authorityService.save(authority);
    userProfileService.save(userProfile);
    imageService.save(userPicture.getImage());
    userPictureService.save(userPicture);

    return "redirect:/log-in";
  }
}
