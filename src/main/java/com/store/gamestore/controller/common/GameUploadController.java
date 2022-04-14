package com.store.gamestore.controller.common;

import com.store.gamestore.model.entity.Game;
import com.store.gamestore.model.entity.GameFile;
import com.store.gamestore.model.entity.GameGenre;
import com.store.gamestore.model.entity.GameImage;
import com.store.gamestore.model.entity.GameProfile;
import com.store.gamestore.model.entity.GameplayImagesDTO;
import com.store.gamestore.model.entity.Genre;
import com.store.gamestore.model.entity.GraphicsCard;
import com.store.gamestore.model.entity.OperatingSystem;
import com.store.gamestore.model.entity.PictureType;
import com.store.gamestore.model.entity.Processor;
import com.store.gamestore.model.entity.Requirements;
import com.store.gamestore.model.entity.UploadInput;
import com.store.gamestore.model.entity.UploadedGame;
import com.store.gamestore.model.entity.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import com.store.gamestore.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class GameUploadController {

    private final CommonService<User, UUID> userService;
    private final CommonService<Game, UUID> gameService;
    private final CommonService<GameImage, UUID> gameImageService;
    private final CommonService<GameGenre, UUID> gameGenreService;
    private final CommonService<GameFile, Integer> gameFileService;
    private final CommonService<GameProfile, Integer> gameProfileService;
    private final CommonService<Requirements, Integer> requirementsService;
    private final CommonService<UploadedGame, UUID> uploadedGameService;
    private final CommonEnumerationService<Genre, Integer> genreService;
    private final CommonEnumerationService<Processor, Integer> processorService;
    private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
    private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;

    @Autowired
    public GameUploadController(
        CommonService<User, UUID> userService,
        CommonService<Game, UUID> gameService,
        CommonService<GameImage, UUID> gameImageService,
        CommonService<GameGenre, UUID> gameGenreService,
        CommonService<GameFile, Integer> gameFileService,
        CommonService<GameProfile, Integer> gameProfileService,
        CommonService<Requirements, Integer> requirementsService,
        CommonEnumerationService<Genre, Integer> genreService,
        CommonEnumerationService<Processor, Integer> processorService,
        CommonEnumerationService<GraphicsCard, Integer> graphicsCardService,
        CommonEnumerationService<OperatingSystem, Integer> operatingSystemService,
        @Qualifier("uploadedGameService") CommonService<UploadedGame, UUID> uploadedGameService) {

        this.userService = userService;
        this.gameService = gameService;
        this.genreService = genreService;
        this.gameFileService = gameFileService;
        this.gameImageService = gameImageService;
        this.gameGenreService = gameGenreService;
        this.gameProfileService = gameProfileService;
        this.requirementsService = requirementsService;
        this.uploadedGameService = uploadedGameService;
        this.processorService = processorService;
        this.graphicsCardService = graphicsCardService;
        this.operatingSystemService = operatingSystemService;
    }

    @GetMapping
    public String homepage(Model model) {
        final String onlyLetters = "^[a-zA-Z]+$";
        final String onlyDigits = "^[\\d]+$";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);
        model.addAttribute("user", user);

        model.addAttribute("uploadInput", new UploadInput());
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
    public String uploadFile(@ModelAttribute UploadInput uploadInput,
                             BindingResult bindingResult,
                             RedirectAttributes attributes) throws IOException {

        if (uploadInput.getFile().isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        Game game = gameService.save(new Game(UUID.randomUUID(), new HashSet<>(),
            new GameProfile(), new GameGenre()));

        gameFileService.save(new GameFile(0, 1000, "",
            uploadInput.getVersion(), uploadInput.getFile(), game.getId()));

        LocalDateTime releaseDate = LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        GameProfile gameProfile = new GameProfile(0, uploadInput.getPrice(), uploadInput.getTitle(),
            uploadInput.getDeveloper(), uploadInput.getPublisher(), 0, 0, 0, 0,
            releaseDate, uploadInput.getDescription(), uploadInput.getSmallDescription(),
            game.getId());
        GameProfile savedGameProfile = gameProfileService.save(gameProfile);

        Requirements requirements = new Requirements(0, uploadInput.getMinMemory(),
            uploadInput.getRecMemory(), uploadInput.getMinStorage(),
            uploadInput.getRecStorage(), savedGameProfile.getId(),
            uploadInput.getMinProcessorId(), uploadInput.getRecProcessorId(),
            uploadInput.getMinGraphicCardId(), uploadInput.getRecGraphicCardId(),
            uploadInput.getMinOSId(), uploadInput.getRecOSId());
        requirementsService.save(requirements);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);

        UploadedGame uploadedGame = new UploadedGame(user, game);
        uploadedGameService.save(uploadedGame);

        Set<Genre> genres = new HashSet<>();
        for (Integer genre : uploadInput.getGenres()) {
            genres.add(genreService.get(genre));
        }

        GameGenre gameGenre = new GameGenre(genres, game.getId());
        gameGenreService.save(gameGenre);

        GameImage storeImage = new GameImage(uploadedGame.getGame().getId(), PictureType.STORE.toString(),
            uploadInput.getGameImages().getStoreImage().getInputStream().readAllBytes());
        GameImage gamePageImage = new GameImage(uploadedGame.getGame().getId(), PictureType.GAMEPAGE.toString(),
            uploadInput.getGameImages().getGamePageImage().getInputStream().readAllBytes());
        GameImage collectionImage = new GameImage(uploadedGame.getGame().getId(), PictureType.COLLECTION.toString(),
            uploadInput.getGameImages().getCollectionImage().getInputStream().readAllBytes());
        gameImageService.save(storeImage);
        gameImageService.save(gamePageImage);
        gameImageService.save(collectionImage);

        for (var image : uploadInput.getGameImages().getGameplayImages()) {
            GameImage gameImage = new GameImage(uploadedGame.getGame().getId(),
                PictureType.GAMEPLAY.getType(), image.getBytes());
            gameImageService.save(gameImage);
        }

        attributes.addFlashAttribute("message", "Your file successfully uploaded "
            + uploadInput.getFile().getOriginalFilename() + '!');

        return "redirect:/uploaded-games";
    }
}

