package com.store.gamestore.controller;

import com.store.gamestore.model.Game;
import com.store.gamestore.model.GameProfile;
import com.store.gamestore.model.Requirements;
import com.store.gamestore.model.UploadInput;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.requirements.RequirementsService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class GameUploadController {

    private final CommonService<Game, Integer> gameService;

    private final CommonService<GameProfile, Integer> gameProfileService;

    private final CommonService<Requirements, Integer> requirementsService;

    @Autowired
    public GameUploadController(
        CommonService<Game, Integer> gameService,
        CommonService<GameProfile, Integer> gameProfileService,
        CommonService<Requirements, Integer> requirementsService) {

        this.gameService = gameService;
        this.gameProfileService = gameProfileService;
        this.requirementsService = requirementsService;
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
        Game savedGame = gameService.save(new Game(0, 1000, file));
        LocalDateTime releaseDate = LocalDateTime.parse(uploadInput.getRelease(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        GameProfile gameProfile = new GameProfile(0, uploadInput.getPrice(), uploadInput.getName(),
            uploadInput.getDeveloper(), uploadInput.getPublisher(), 0,
            releaseDate, uploadInput.getDescription(), uploadInput.getSmallDescription(),
            savedGame.getId());
        GameProfile savedGameProfile = gameProfileService.save(gameProfile);

        Requirements requirements = new Requirements(0, savedGameProfile.getGameId(), uploadInput.getMinMemory(),
            uploadInput.getRecMemory(), uploadInput.getMinStorage(), uploadInput.getRecStorage(),
            uploadInput.getMinProcessorId(), uploadInput.getRecProcessorId(),
            uploadInput.getMinGraphicCardId(), uploadInput.getRecGraphicCardId(),
            uploadInput.getMinOSId(), uploadInput.getRecOSId());
        requirementsService.save(requirements);

        attributes.addFlashAttribute("message", "Your file successfully uploaded "
            + file.getOriginalFilename() + '!');

        return "redirect:/";
    }

}

