package com.store.gamestore.controller.common;

import com.store.gamestore.model.entity.EditGameInput;
import com.store.gamestore.model.entity.Game;
import com.store.gamestore.model.entity.GameFile;
import com.store.gamestore.model.entity.GameGenre;
import com.store.gamestore.model.entity.GameImage;
import com.store.gamestore.model.entity.GameProfile;
import com.store.gamestore.model.entity.Genre;
import com.store.gamestore.model.entity.GraphicsCard;
import com.store.gamestore.model.entity.OperatingSystem;
import com.store.gamestore.model.entity.PictureType;
import com.store.gamestore.model.entity.Processor;
import com.store.gamestore.model.entity.Requirements;
import com.store.gamestore.model.entity.UploadedGame;
import com.store.gamestore.model.entity.UploadedGameDTO;
import com.store.gamestore.model.entity.User;
import com.store.gamestore.model.util.GamePicturesUtil;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import com.store.gamestore.service.user.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/uploaded-games")
public class UploadedGamesController {

    private final CommonService<User, UUID> userService;
    private final CommonService<Game, UUID> gameService;
    private final CommonService<GameImage, UUID> gameImageService;
    private final CommonService<GameGenre, UUID> gameGenreService;
    private final CommonService<GameFile, Integer> gameFileService;
    private final CommonService<UploadedGame, UUID> uploadedGameService;
    private final CommonService<GameProfile, Integer> gameProfileService;
    private final CommonService<Requirements, Integer> requirementsService;
    private final CommonEnumerationService<Genre, Integer> genreService;
    private final CommonEnumerationService<Processor, Integer> processorService;
    private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
    private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;

    @Autowired
    public UploadedGamesController(CommonService<User, UUID> userService,
                                   CommonService<Game, UUID> gameService,
                                   CommonService<GameImage, UUID> gameImageService,
                                   CommonService<GameGenre, UUID> gameGenreService,
                                   CommonService<GameFile, Integer> gameFileService,
                                   @Qualifier("uploadedGameService")
                                       CommonService<UploadedGame, UUID> uploadedGameService,
                                   CommonService<GameProfile, Integer> gameProfileService,
                                   CommonService<Requirements, Integer> requirementsService,
                                   CommonEnumerationService<Genre, Integer> genreService,
                                   CommonEnumerationService<Processor, Integer> processorService,
                                   CommonEnumerationService<GraphicsCard, Integer> graphicsCardService,
                                   CommonEnumerationService<OperatingSystem, Integer> operatingSystemService
    ) {
        this.gameService = gameService;
        this.userService = userService;
        this.gameImageService = gameImageService;
        this.gameGenreService = gameGenreService;
        this.genreService = genreService;
        this.gameFileService = gameFileService;
        this.requirementsService = requirementsService;
        this.gameProfileService = gameProfileService;
        this.uploadedGameService = uploadedGameService;
        this.processorService = processorService;
        this.graphicsCardService = graphicsCardService;
        this.operatingSystemService = operatingSystemService;
    }

    @GetMapping
    public String uploadedGamesPage(Model model) {

        User user = getUser();
        model.addAttribute("user", user);
        List<UploadedGame> uploadedGames = uploadedGameService.getAll(user.getId());
        List<UploadedGameDTO> dtos = new ArrayList<>();
        for (UploadedGame game : uploadedGames) {
            List<GameImage> gameImages = gameImageService.getAll(game.getGame().getId());
            GameImage image = GamePicturesUtil.getGamePicture(gameImages, PictureType.GAMEPAGE);
            dtos.add(new UploadedGameDTO(game, image));
        }

        model.addAttribute("uploadedGames", dtos);
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
        return ((UserDetailsService) userService).get(name);
    }

    @PostMapping("/edit/{id}/files")
    public String postGameFile(@PathVariable String id,
                               @RequestParam("version") String version,
                               @RequestParam("file") MultipartFile file) {
        GameFile gameFile = new GameFile(0, 1000, "", version, file, UUID.fromString(id));
        gameFileService.save(gameFile);

        return "redirect:/uploaded-games/edit/" + id;
    }

    @PostMapping("/edit/{id}/files/{fileId}/delete")
    public String editGame(@PathVariable Integer fileId,
                           @PathVariable String id,
                           Model model) {
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

    @PostMapping("/edit/{id}")
    public String editGame(@ModelAttribute EditGameInput editGameInput,
                           BindingResult bindingResult,
                           @PathVariable String id) {

        UploadedGame game = uploadedGameService.get(UUID.fromString(id));
        LocalDateTime releaseDate = LocalDateTime.parse(game.getGame().getGameProfile().getReleaseDate().toString(),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        GameProfile gameProfile = new GameProfile(game.getGame().getGameProfile().getId(),
            editGameInput.getPrice(), editGameInput.getTitle(), editGameInput.getDeveloper(),
            editGameInput.getPublisher(), 0, 0, 0, 0,
            releaseDate, editGameInput.getDescription(),
            editGameInput.getSmallDescription(), UUID.fromString(id));
        gameProfileService.update(gameProfile);

        Requirements requirements = requirementsService.get(gameProfile.getId());
        Requirements newRequirements = new Requirements(requirements.getId(), editGameInput.getMinMemory(),
            editGameInput.getRecMemory(), editGameInput.getMinStorage(),
            editGameInput.getRecStorage(), gameProfile.getId(),
            editGameInput.getMinProcessorId(), editGameInput.getRecProcessorId(),
            editGameInput.getMinGraphicCardId(), editGameInput.getRecGraphicCardId(),
            editGameInput.getMinOSId(), editGameInput.getRecOSId());
        requirementsService.update(newRequirements);

        Set<Genre> genres = new HashSet<>();
        for (Integer genre : editGameInput.getGenres()) {
            genres.add(genreService.get(genre));
        }

        GameGenre gameGenre = new GameGenre(genres, UUID.fromString(id));
        gameGenreService.update(gameGenre);

        return "redirect:/uploaded-games/edit/" + id;
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
        log.info("game profile id: " + gameProfileId);
        Requirements requirements = requirementsService.get(gameProfileId);

        model.addAttribute("editGameInput", new EditGameInput(uploadedGame, requirements));
        return "edit-game";
    }
}
