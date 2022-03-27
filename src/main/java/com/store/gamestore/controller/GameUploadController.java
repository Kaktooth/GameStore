package com.store.gamestore.controller;

import com.store.gamestore.model.Game;
import com.store.gamestore.model.GameFile;
import com.store.gamestore.model.GameGenre;
import com.store.gamestore.model.GameProfile;
import com.store.gamestore.model.Genre;
import com.store.gamestore.model.GraphicsCard;
import com.store.gamestore.model.OperatingSystem;
import com.store.gamestore.model.Processor;
import com.store.gamestore.model.Requirements;
import com.store.gamestore.model.UploadInput;
import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.model.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import com.store.gamestore.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        CommonService<GameGenre, UUID> gameGenreService,
        CommonService<GameFile, Integer> gameFileService,
        CommonService<GameProfile, Integer> gameProfileService,
        CommonService<Requirements, Integer> requirementsService,
        CommonService<UploadedGame, UUID> uploadedGameService,
        CommonEnumerationService<Genre, Integer> genreService,
        CommonEnumerationService<Processor, Integer> processorService,
        CommonEnumerationService<GraphicsCard, Integer> graphicsCardService,
        CommonEnumerationService<OperatingSystem, Integer> operatingSystemService) {

        this.userService = userService;
        this.gameService = gameService;
        this.genreService = genreService;
        this.gameFileService = gameFileService;
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
        model.addAttribute("uploadInput", new UploadInput());
        model.addAttribute("genreList", genreService.getAll());

        model.addAttribute("processors", processorService.getAll());
        model.addAttribute("graphicCards", graphicsCardService.getAll());
        model.addAttribute("osList", operatingSystemService.getAll());

        return "upload";
    }

    @PostMapping
    public String uploadFile(@ModelAttribute UploadInput uploadInput,
                             @RequestParam("file") MultipartFile file,
                             BindingResult bindingResult,
                             RedirectAttributes attributes) {

        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        Game game = gameService.save(new Game(UUID.randomUUID(), new HashSet<>(),
            new GameProfile(), new GameGenre()));

        gameFileService.save(new GameFile(0, 1000, "",
            uploadInput.getVersion(), file, game.getId()));

        LocalDateTime releaseDate = LocalDateTime.parse(uploadInput.getRelease(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        GameProfile gameProfile = new GameProfile(0, uploadInput.getPrice(), uploadInput.getName(),
            uploadInput.getDeveloper(), uploadInput.getPublisher(), 0,
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

        attributes.addFlashAttribute("message", "Your file successfully uploaded "
            + file.getOriginalFilename() + '!');

        return "redirect:/uploaded-games";
    }
}

