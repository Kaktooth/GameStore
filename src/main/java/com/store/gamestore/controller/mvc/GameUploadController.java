package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.dto.GameplayImagesDTO;
import com.store.gamestore.model.dto.UploadGameDTO;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GameFile;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.GameProfile;
import com.store.gamestore.persistence.entity.Genre;
import com.store.gamestore.persistence.entity.GraphicsCard;
import com.store.gamestore.persistence.entity.Image;
import com.store.gamestore.persistence.entity.OperatingSystem;
import com.store.gamestore.persistence.entity.Processor;
import com.store.gamestore.persistence.entity.SystemRequirements;
import com.store.gamestore.persistence.entity.UploadedGame;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import com.store.gamestore.service.user.UserService;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.sql.rowset.serial.SerialBlob;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/upload")
@AllArgsConstructor
public class GameUploadController {

  private final CommonService<User, UUID> userService;
  private final CommonService<Game, UUID> gameService;
  private final CommonService<Image, UUID> imageService;
  private final CommonService<GamePicture, UUID> gameImageService;
  private final CommonService<GameFile, UUID> gameFileService;
  private final CommonService<GameProfile, UUID> gameProfileService;
  private final CommonService<SystemRequirements, UUID> requirementsService;
  private final CommonService<UploadedGame, UUID> uploadedGameService;
  private final CommonEnumerationService<Genre, Integer> genreService;
  private final CommonEnumerationService<Processor, Integer> processorService;
  private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
  private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;

  @GetMapping
  public String getUploadPage(Model model) {
    final String onlyLetters = "^[a-zA-Z]+$";
    final String onlyDigits = "^[\\d]+$";
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    User user = ((UserService) userService).findUserByUsername(name);
    model.addAttribute("user", user);

    model.addAttribute("uploadInput", new UploadGameDTO());
    model.addAttribute("gameplayImages", new GameplayImagesDTO());
    model.addAttribute("genreList", genreService.getAll());

    model.addAttribute("processors", processorService.getAll());
    model.addAttribute("graphicCards", graphicsCardService.getAll());
    model.addAttribute("osList", operatingSystemService.getAll());

    model.addAttribute("onlyLetters", onlyLetters);
    model.addAttribute("onlyDigits", onlyDigits);
    return "upload";
  }

  @PostMapping
  public String uploadFile(@ModelAttribute UploadGameDTO uploadInput, RedirectAttributes attributes)
      throws IOException, SQLException {

    if (uploadInput.getFile().isEmpty()) {
      attributes.addFlashAttribute("message", "Please select a file to upload.");
      return "redirect:/";
    }

    Set<Genre> genres = new HashSet<>();
    for (Integer genre : uploadInput.getGenres()) {
      genres.add(genreService.get(genre));
    }

    Game game = gameService.save(
        new Game(uploadInput.getTitle(), uploadInput.getPrice(), uploadInput.getDeveloper(),
            uploadInput.getPublisher(), genres));

    var blob = new SerialBlob(uploadInput.getFile().getBytes());
    gameFileService.save(new GameFile(uploadInput.getFile().getOriginalFilename(),
        uploadInput.getVersion(), game.getId(), blob));

    GameProfile gameProfile = new GameProfile(LocalDate.now(), uploadInput.getDescription(),
        uploadInput.getSmallDescription(), game.getId());
    GameProfile savedGameProfile = gameProfileService.save(gameProfile);

    SystemRequirements requirements = new SystemRequirements(uploadInput.getMinMemory(),
        uploadInput.getRecMemory(), uploadInput.getMinStorage(), uploadInput.getRecStorage(),
        savedGameProfile.getId(), uploadInput.getMinProcessorId(), uploadInput.getRecProcessorId(),
        uploadInput.getMinGraphicCardId(), uploadInput.getRecGraphicCardId(),
        uploadInput.getMinOSId(), uploadInput.getRecOSId());
    requirementsService.save(requirements);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    User user = ((UserService) userService).findUserByUsername(name);

    UploadedGame uploadedGame = new UploadedGame(user.getId(), game);
    uploadedGameService.save(uploadedGame);

    Image storeImage = imageService.save(
        new Image(uploadInput.getGameImages().getStoreImage().getInputStream().readAllBytes()));
    GamePicture storeGameImage = new GamePicture(game.getId(), GamePictureType.STORE.ordinal(),
        storeImage);

    Image pageImage = imageService.save(
        new Image(uploadInput.getGameImages().getGamePageImage().getInputStream().readAllBytes()));
    GamePicture gamePageImage = new GamePicture(game.getId(), GamePictureType.GAME_PAGE.ordinal(),
        pageImage);

    Image collectionImage = imageService.save(new Image(
        uploadInput.getGameImages().getCollectionImage().getInputStream().readAllBytes()));
    GamePicture collectionGameImage = new GamePicture(game.getId(),
        GamePictureType.COLLECTION.ordinal(), collectionImage);
    gameImageService.save(storeGameImage);
    gameImageService.save(gamePageImage);
    gameImageService.save(collectionGameImage);

    for (var image : uploadInput.getGameImages().getGameplayImages()) {
      Image gameplayImage = imageService.save(new Image(image.getBytes()));
      GamePicture gameplayGamePicture = new GamePicture(game.getId(),
          GamePictureType.GAMEPLAY.ordinal(), gameplayImage);
      gameImageService.save(gameplayGamePicture);
    }

    attributes.addFlashAttribute("message",
        String.format("Your game and file successfully uploaded %s!",
            uploadInput.getFile().getOriginalFilename()));

    return "redirect:/uploaded-games";
  }
}

