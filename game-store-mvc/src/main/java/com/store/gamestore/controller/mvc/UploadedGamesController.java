package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.dto.EditGameDTO;
import com.store.gamestore.common.mapper.UploadedGameMapper;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GameFile;
import com.store.gamestore.persistence.entity.Genre;
import com.store.gamestore.persistence.entity.GraphicsCard;
import com.store.gamestore.persistence.entity.OperatingSystem;
import com.store.gamestore.persistence.entity.Processor;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import com.store.gamestore.service.game.file.GameFileService;
import com.store.gamestore.service.game.profile.GameProfileService;
import com.store.gamestore.service.game.uploaded.UploadedGameService;
import com.store.gamestore.service.requirements.SystemRequirementsService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.rowset.serial.SerialBlob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

  private static final String EDIT_PAGE_REDIRECT = "redirect:/uploaded-games/edit/";

  private final UserHolder userHolder;
  private final CommonService<Game, UUID> gameService;
  private final UploadedGameMapper uploadedGameMapper;
  private final GameFileService gameFileService;
  private final UploadedGameService uploadedGameService;
  private final GameProfileService gameProfileService;
  private final SystemRequirementsService requirementsService;
  private final CommonEnumerationService<Genre, Integer> genreService;
  private final CommonEnumerationService<Processor, Integer> processorService;
  private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
  private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;

  @GetMapping
  public String uploadedGamesPage(Model model) {

    var user = userHolder.getAuthenticated();
    model.addAttribute("user", userHolder.getAuthenticated());

    var uploadedGames = uploadedGameService.findAllByUserId(user.getId());
    var uploadedGameDTOList = uploadedGameMapper.sourceToDestination(uploadedGames);
    log.info("Uploaded games list loaded... size of uploaded games list: {}", uploadedGames.size());

    model.addAttribute("uploadedGames", uploadedGameDTOList);
    return "uploaded-games";
  }

  @GetMapping("/edit/{gameId}/files")
  public String gameFilesPage(@PathVariable UUID gameId, Model model) {

    model.addAttribute("user", userHolder.getAuthenticated());

    var gameFiles = gameFileService.findAllByGameId(gameId);
    var game = gameService.get(gameId);
    model.addAttribute("gameFiles", gameFiles);
    model.addAttribute("uploadedGame", game);
    model.addAttribute("version", "");
    return "files";
  }

  @PostMapping("/edit/{gameId}/files")
  public String addGameFile(@PathVariable UUID gameId, @RequestParam("version") String version,
      @RequestParam("file") MultipartFile file) throws IOException, SQLException {

    var blob = new SerialBlob(file.getBytes());
    var gameFile = new GameFile(file.getOriginalFilename(), version, gameId, blob);
    gameFileService.save(gameFile);

    return EDIT_PAGE_REDIRECT + gameId + "/files";
  }

  @PostMapping("/edit/{gameId}/files/{fileId}/delete")
  public String editGame(@PathVariable UUID fileId, @PathVariable UUID gameId) {
    gameFileService.delete(fileId);
    return EDIT_PAGE_REDIRECT + gameId + "/files";
  }

  @PostMapping("/edit/{gameId}/delete")
  public String editGame(@PathVariable UUID gameId, Model model) {
    gameService.delete(gameId);
    model.addAttribute("user", userHolder.getAuthenticated());
    return "uploaded-games";
  }

  @GetMapping("/edit/{gameId}")
  public String uploadedGamePage(@PathVariable UUID gameId, Model model) {
    model.addAttribute("user", userHolder.getAuthenticated());
    model.addAttribute("genreList", genreService.getAll());
    model.addAttribute("processors", processorService.getAll());
    model.addAttribute("graphicCards", graphicsCardService.getAll());
    model.addAttribute("osList", operatingSystemService.getAll());

    final var game = gameService.get(gameId);
    final var gameProfile = gameProfileService.findGameProfileByGameId(gameId);
    final var requirements = requirementsService.findByGameProfileId(gameProfile.getId());
    final var editGameDTO = new EditGameDTO(game, gameProfile, requirements);
    model.addAttribute("uploadedGame", game);
    model.addAttribute("editGameInput", editGameDTO);

    log.info("edited game with id: {}", gameId.toString());
    return "edit-game";
  }

  @PostMapping("/edit/{gameId}")
  public String editGame(@ModelAttribute EditGameDTO editGameInput, @PathVariable UUID gameId) {

    var game = gameService.get(gameId);
    game.setDeveloper(editGameInput.getDeveloper());
    game.setPublisher(editGameInput.getPublisher());

    var genres = genreService.getAll(editGameInput.getGenres());
    game.setGenres(genres);
    game.setTitle(editGameInput.getTitle());
    game.setPrice(editGameInput.getPrice());
    gameService.update(game);

    var gameProfile = gameProfileService.findGameProfileByGameId(game.getId());
    gameProfile.setDescription(editGameInput.getDescription());
    gameProfile.setBriefDescription(editGameInput.getSmallDescription());
    gameProfileService.update(gameProfile);

    var systemRequirements = requirementsService.findByGameProfileId(gameProfile.getId());
    systemRequirements.setMinimalMemory(editGameInput.getMinMemory());
    systemRequirements.setRecommendedMemory(editGameInput.getRecMemory());
    systemRequirements.setMinimalStorage(editGameInput.getMinStorage());
    systemRequirements.setRecommendedStorage(editGameInput.getRecStorage());
    systemRequirements.setMinimalProcessorId(editGameInput.getMinProcessorId());
    systemRequirements.setRecommendedProcessorId(editGameInput.getRecProcessorId());
    systemRequirements.setMinimalGraphicCardId(editGameInput.getMinGraphicCardId());
    systemRequirements.setRecommendedGraphicCardId(editGameInput.getRecGraphicCardId());
    systemRequirements.setMinimalOperatingSystemId(editGameInput.getMinOSId());
    systemRequirements.setRecommendedOperatingSystemId(editGameInput.getRecOSId());
    requirementsService.update(systemRequirements);

    return EDIT_PAGE_REDIRECT + gameId;
  }
}
