package com.store.gamestore.controller;

import com.store.gamestore.model.Game;
import com.store.gamestore.model.GameFile;
import com.store.gamestore.model.GameProfile;
import com.store.gamestore.model.Requirements;
import com.store.gamestore.model.UploadInput;
import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.model.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.requirements.RequirementsService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
public class GameUploadController {

    private final CommonService<User, UUID> userService;
    private final CommonService<Game, Integer> gameService;
    private final CommonService<GameFile, Integer> gameFileService;
    private final CommonService<GameProfile, Integer> gameProfileService;
    private final CommonService<Requirements, Integer> requirementsService;
    private final CommonService<UploadedGame, Integer> uploadedGameService;

    @Autowired
    public GameUploadController(
        CommonService<User, UUID> userService,
        CommonService<Game, Integer> gameService,
        CommonService<GameFile, Integer> gameFileService,
        CommonService<GameProfile, Integer> gameProfileService,
        CommonService<Requirements, Integer> requirementsService,
        CommonService<UploadedGame, Integer> uploadedGameService) {

        this.userService = userService;
        this.gameService = gameService;
        this.gameFileService = gameFileService;
        this.gameProfileService = gameProfileService;
        this.requirementsService = requirementsService;
        this.uploadedGameService = uploadedGameService;
    }

    @GetMapping("/upload")
    public String homepage(Model model) {
        model.addAttribute("uploadInput", new UploadInput());
        model.addAttribute("processors",
            ((RequirementsService) requirementsService).getProcessorNames());
        model.addAttribute("graphicCards",
            ((RequirementsService) requirementsService).getGraphicsCardNames());
        model.addAttribute("osList",
            ((RequirementsService) requirementsService).getOSNames());
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute UploadInput uploadInput,
                             @RequestParam("file") MultipartFile file,
                             BindingResult bindingResult,
                             RedirectAttributes attributes) {

        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }
        Game game = gameService.save(new Game(UUID.randomUUID()));

        gameFileService.save(new GameFile(0, 1000, "1.0", file, game.getId()));
        LocalDateTime releaseDate = LocalDateTime.parse(uploadInput.getRelease(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        GameProfile gameProfile = new GameProfile(0, uploadInput.getPrice(), uploadInput.getName(),
            uploadInput.getDeveloper(), uploadInput.getPublisher(), 0,
            releaseDate, uploadInput.getDescription(), uploadInput.getSmallDescription(),
            game.getId());
        GameProfile savedGameProfile = gameProfileService.save(gameProfile);

        Requirements requirements = new Requirements(0, savedGameProfile.getId(), uploadInput.getMinMemory(),
            uploadInput.getRecMemory(), uploadInput.getMinStorage(), uploadInput.getRecStorage(),
            uploadInput.getMinProcessorId(), uploadInput.getRecProcessorId(),
            uploadInput.getMinGraphicCardId(), uploadInput.getRecGraphicCardId(),
            uploadInput.getMinOSId(), uploadInput.getRecOSId());
        requirementsService.save(requirements);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);

        UploadedGame uploadedGame = new UploadedGame(user, game);
        uploadedGameService.save(uploadedGame);

        attributes.addFlashAttribute("message", "Your file successfully uploaded "
            + file.getOriginalFilename() + '!');

        return "redirect:/";
    }

}

