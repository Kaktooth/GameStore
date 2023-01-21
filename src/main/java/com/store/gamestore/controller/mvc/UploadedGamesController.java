package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.dto.EditGameDTO;
import com.store.gamestore.model.dto.UploadedGameDTO;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GameFile;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.GameProfile;
import com.store.gamestore.persistence.entity.Genre;
import com.store.gamestore.persistence.entity.GraphicsCard;
import com.store.gamestore.persistence.entity.OperatingSystem;
import com.store.gamestore.persistence.entity.Processor;
import com.store.gamestore.persistence.entity.SystemRequirements;
import com.store.gamestore.persistence.entity.UploadedGame;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import com.store.gamestore.service.game.file.GameFileService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.game.profile.GameProfileService;
import com.store.gamestore.service.game.uploaded.UploadedGameService;
import com.store.gamestore.service.requirements.SystemRequirementsService;
import com.store.gamestore.service.user.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/uploaded-games")
@RequiredArgsConstructor
public class UploadedGamesController {

  private final UserService userService;
  private final CommonService<Game, UUID> gameService;
  private final GamePictureService gameImageService;
  private final CommonService<GameFile, UUID> gameFileService;
  private final UploadedGameService uploadedGameService;
  private final CommonService<GameProfile, UUID> gameProfileService;
  private final CommonService<SystemRequirements, UUID> requirementsService;
  private final CommonEnumerationService<Genre, Integer> genreService;
  private final CommonEnumerationService<Processor, Integer> processorService;
  private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
  private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;

  @GetMapping
  public String uploadedGamesPage(Model model) {

    final var user = getUser();
    model.addAttribute("user", user);
    //TODO refactor this peace of code. Add to repository get all games by id
    // and change here.
    List<UploadedGame> uploadedGames = uploadedGameService.findAllByUserId(user.getId());
    List<UploadedGameDTO> uploadedGameDTOList = new ArrayList<>();
    for (var game : uploadedGames) {
      GamePicture gamePicture = gameImageService.findGamePictureByGameIdAndPictureTypeId(
          game.getGame().getId(), GamePictureType.STORE.ordinal());
      uploadedGameDTOList.add(new UploadedGameDTO(game, gamePicture));
    }
    log.info("Uploaded games list loaded... size of uploaded games list: {}", uploadedGames.size());

    model.addAttribute("uploadedGames", uploadedGameDTOList);
    return "uploaded-games";
  }

  @GetMapping("/edit/{gameId}/files")
  public String gameFilesPage(@PathVariable UUID gameId, Model model) {

    User user = getUser();
    model.addAttribute("user", user);

    List<GameFile> gameFiles = ((GameFileService) gameFileService).findAllByGameId(gameId);
    Game game = gameService.get(gameId);

    model.addAttribute("gameFiles", gameFiles);
    model.addAttribute("uploadedGame", game);
    model.addAttribute("version", "");

    return "files";
  }

  @PostMapping("/edit/{gameId}/files")
  public String addGameFile(@PathVariable UUID gameId, @RequestParam("version") String version,
      @RequestParam("file") MultipartFile file) throws IOException {

    gameFileService.save(
        new GameFile(file.getOriginalFilename(), file.getInputStream().readAllBytes(), version,
            gameId));

    return "redirect:/uploaded-games/edit/" + gameId + "/files";
  }

  @PostMapping("/edit/{gameId}/files/{fileId}/delete")
  public String editGame(@PathVariable UUID fileId, @PathVariable UUID gameId) {

    gameFileService.delete(fileId);

    return "redirect:/uploaded-games/edit/" + gameId + "/files";
  }

  @PostMapping("/edit/{gameId}/delete")
  public String editGame(@PathVariable UUID gameId, Model model) {

    gameService.delete(gameId);
    User user = getUser();
    model.addAttribute("user", user);

    return "uploaded-games";
  }

  @GetMapping("/edit/{gameId}")
  public String uploadedGamePage(@PathVariable UUID gameId, Model model) {
    User user = getUser();
    model.addAttribute("user", user);

    Game game = gameService.get(gameId);
    model.addAttribute("uploadedGame", game);
    model.addAttribute("genreList", genreService.getAll());
    model.addAttribute("processors", processorService.getAll());
    model.addAttribute("graphicCards", graphicsCardService.getAll());
    model.addAttribute("osList", operatingSystemService.getAll());

    GameProfile gameProfile = ((GameProfileService) gameProfileService).findGameProfileByGameId(
        gameId);
    SystemRequirements requirements = ((SystemRequirementsService) requirementsService)
        .findByGameProfileId(gameProfile.getId());

    model.addAttribute("editGameInput", new EditGameDTO(game, gameProfile, requirements));

    log.info("edited game with id: {}", gameId.toString());
    return "edit-game";
  }

  @PostMapping("/edit/{gameId}")
  public String editGame(@ModelAttribute EditGameDTO editGameInput, @PathVariable UUID gameId) {

    Game game = gameService.get(gameId);
    game.setDeveloper(editGameInput.getDeveloper());
    game.setPublisher(editGameInput.getPublisher());
    //TODO refactor that
    Set<Genre> genres = new HashSet<>();
    for (Integer genre : editGameInput.getGenres()) {
      genres.add(genreService.get(genre));
    }
    game.setGenres(genres);
    game.setTitle(editGameInput.getTitle());
    game.setPrice(editGameInput.getPrice());
    gameService.update(game);

    GameProfile gameProfile = ((GameProfileService) gameProfileService).findGameProfileByGameId(
        game.getId());
    gameProfile.setDescription(editGameInput.getDescription());
    gameProfile.setBriefDescription(editGameInput.getSmallDescription());
    gameProfileService.update(gameProfile);

    SystemRequirements requirements = ((SystemRequirementsService) requirementsService).findByGameProfileId(
        gameProfile.getId());
    requirements.setMinimalMemory(editGameInput.getMinMemory());
    requirements.setRecommendedMemory(editGameInput.getRecMemory());
    requirements.setMinimalStorage(editGameInput.getMinStorage());
    requirements.setRecommendedStorage(editGameInput.getRecStorage());
    requirements.setMinimalProcessorId(editGameInput.getMinProcessorId());
    requirements.setRecommendedProcessorId(editGameInput.getRecProcessorId());
    requirements.setMinimalGraphicCardId(editGameInput.getMinGraphicCardId());
    requirements.setRecommendedGraphicCardId(editGameInput.getRecGraphicCardId());
    requirements.setMinimalOperatingSystemId(editGameInput.getMinOSId());
    requirements.setRecommendedOperatingSystemId(editGameInput.getRecOSId());
    requirementsService.update(requirements);

    return "redirect:/uploaded-games/edit/" + gameId;
  }

  private User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    return userService.findUserByUsername(name);
  }
}
