package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.dto.GameplayImagesDTO;
import com.store.gamestore.model.dto.UploadGameDTO;
import com.store.gamestore.model.util.UserHolder;
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
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.sql.rowset.serial.SerialBlob;
import lombok.AllArgsConstructor;
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

  private final UserHolder userHolder;
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
    model.addAttribute("user", userHolder.getAuthenticated());
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

    List<Genre> genres = new ArrayList<>();
    for (Integer genre : uploadInput.getGenres()) {
      genres.add(genreService.get(genre));
    }

    var game = gameService.save(
        new Game(uploadInput.getTitle(), uploadInput.getPrice(), uploadInput.getDeveloper(),
            uploadInput.getPublisher(), genres));

    var blob = new SerialBlob(uploadInput.getFile().getBytes());
    gameFileService.save(new GameFile(uploadInput.getFile().getOriginalFilename(),
        uploadInput.getVersion(), game.getId(), blob));

    var gameProfile = new GameProfile(LocalDate.now(), uploadInput.getDescription(),
        uploadInput.getSmallDescription(), game.getId());
    var savedGameProfile = gameProfileService.save(gameProfile);

    var requirements = new SystemRequirements(uploadInput.getMinMemory(),
        uploadInput.getRecMemory(), uploadInput.getMinStorage(), uploadInput.getRecStorage(),
        savedGameProfile.getId(), uploadInput.getMinProcessorId(), uploadInput.getRecProcessorId(),
        uploadInput.getMinGraphicCardId(), uploadInput.getRecGraphicCardId(),
        uploadInput.getMinOSId(), uploadInput.getRecOSId());
    requirementsService.save(requirements);

    var user = userHolder.getAuthenticated();

    var uploadedGame = new UploadedGame(user.getId(), game);
    uploadedGameService.save(uploadedGame);

    var storeImage = imageService.save(
        new Image(uploadInput.getGameImages().getStoreImage().getInputStream().readAllBytes()));
    var storeGameImage = new GamePicture(game.getId(), GamePictureType.STORE.ordinal(),
        storeImage);

    var pageImage = imageService.save(
        new Image(uploadInput.getGameImages().getGamePageImage().getInputStream().readAllBytes()));
    var gamePageImage = new GamePicture(game.getId(), GamePictureType.GAME_PAGE.ordinal(),
        pageImage);

    var collectionImage = imageService.save(new Image(
        uploadInput.getGameImages().getCollectionImage().getInputStream().readAllBytes()));
    var collectionGameImage = new GamePicture(game.getId(),
        GamePictureType.COLLECTION.ordinal(), collectionImage);
    gameImageService.save(storeGameImage);
    gameImageService.save(gamePageImage);
    gameImageService.save(collectionGameImage);

    for (var image : uploadInput.getGameImages().getGameplayImages()) {
      var gameplayImage = imageService.save(new Image(image.getBytes()));
      var gameplayGamePicture = new GamePicture(game.getId(),
          GamePictureType.GAMEPLAY.ordinal(), gameplayImage);
      gameImageService.save(gameplayGamePicture);
    }

    attributes.addFlashAttribute("message",
        String.format("Your game and file successfully uploaded %s!",
            uploadInput.getFile().getOriginalFilename()));

    return "redirect:/uploaded-games";
  }
}

