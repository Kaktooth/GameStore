package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.dto.EditGameDTO;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GameFile;
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
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.game.profile.GameProfileService;
import com.store.gamestore.service.requirements.SystemRequirementsService;
import com.store.gamestore.service.user.UserService;
import com.store.gamestore.service.user.profile.UserProfileService;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
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
  private final UserProfileService userProfileService;
  private final CommonService<Game, UUID> gameService;
  private final GamePictureService gameImageService;
  private final CommonService<GameFile, Integer> gameFileService;
  private final CommonService<UploadedGame, UUID> uploadedGameService;
  private final CommonService<GameProfile, Integer> gameProfileService;
  private final CommonService<SystemRequirements, Integer> requirementsService;
  private final CommonEnumerationService<Genre, Integer> genreService;
  private final CommonEnumerationService<Processor, Integer> processorService;
  private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
  private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;

  @GetMapping
  public String uploadedGamesPage(Model model) {

    final var user = getUser();
    final var userProfile = userProfileService.getUserProfile(user.getId());
    model.addAttribute("user", user);
    model.addAttribute("userProfile", userProfile);
    //TODO refactor this peace of code. Add to repository get all games by id
    // and change here.
    List<UploadedGame> uploadedGames = uploadedGameService.getAll(Set.of(user.getId()));

    var ids = uploadedGames
        .stream()
        .map(UploadedGame::getId)
        .collect(Collectors.toList());
    final var uploadedGameImages = gameImageService.findGamePictureByGameIdsAndPictureTypeId(
        ids, GamePictureType.GAME_PAGE.ordinal());

    model.addAttribute("uploadedGames", uploadedGames);
    model.addAttribute("uploadedGameImages", uploadedGameImages);

    return "uploaded-games";
  }

  @GetMapping("/edit/{id}/files")
  public String gameFilesPage(@PathVariable String id,
      Model model) {

    User user = getUser();
    model.addAttribute("user", user);
    log.info(id);
    UUID gameId = UUID.fromString(id);
    UploadedGame uploadedGame = uploadedGameService.get(gameId);
    model.addAttribute("uploadedGame", uploadedGame);
    model.addAttribute("version", "");

    return "files";
  }

  private User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    return userService.findUserByUsername(name);
  }

  @PostMapping("/edit/{id}/files")
  public String addGameFile(@PathVariable String id, @RequestParam("version") String version,
      @RequestParam("file") MultipartFile file) throws IOException {

    gameFileService.save(new GameFile(file.getOriginalFilename(),
        file.getInputStream().readAllBytes(), version, UUID.fromString(id)));

    return "redirect:/uploaded-games/edit/" + id + "/files";
  }

  @PostMapping("/edit/{id}/files/{fileId}/delete")
  public String editGame(@PathVariable Integer fileId,
      @PathVariable String id) {

    gameFileService.delete(fileId);

    return "redirect:/uploaded-games/edit/" + id + "/files";
  }

  @PostMapping("/edit/{id}/delete")
  public String editGame(@PathVariable String id,
      Model model) {

    UUID gameId = UUID.fromString(id);
    gameService.delete(gameId);
    User user = getUser();
    model.addAttribute("user", user);

    return "uploaded-games";
  }

  @GetMapping("/edit/{id}")
  public String uploadedGamePage(@PathVariable String id,
      Model model) {
    User user = getUser();
    model.addAttribute("user", user);
    log.info(id);
    UUID gameId = UUID.fromString(id);
    UploadedGame uploadedGame = uploadedGameService.get(gameId);
    model.addAttribute("uploadedGame", uploadedGame);
    model.addAttribute("genreList", genreService.getAll());
    model.addAttribute("processors", processorService.getAll());
    model.addAttribute("graphicCards", graphicsCardService.getAll());
    model.addAttribute("osList", operatingSystemService.getAll());

    Integer gameProfileId = uploadedGame.getGame().getGameProfile().getId();
    log.info("loaded game profile id: " + gameProfileId);
    Requirements requirements = requirementsService.get(gameProfileId);

    model.addAttribute("editGameInput", new EditGameInput(uploadedGame, requirements));
    return "edit-game";
  }

  @PostMapping("/edit/{id}")
  public String editGame(@ModelAttribute EditGameDTO editGameInput,
      BindingResult bindingResult,
      @PathVariable String id) {

    Game game = gameService.get(UUID.fromString(id));
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

    SystemRequirements requirements = ((SystemRequirementsService) requirementsService)
        .findByGameProfileId(gameProfile.getId());
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

    return "redirect:/uploaded-games/edit/" + id;
  }
}
