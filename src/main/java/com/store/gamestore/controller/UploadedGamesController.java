package com.store.gamestore.controller;

import com.store.gamestore.model.Genre;
import com.store.gamestore.model.Requirements;
import com.store.gamestore.model.UploadInput;
import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.model.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/uploaded-games")
public class UploadedGamesController {

    private final CommonService<User, UUID> userService;
    private final CommonService<UploadedGame, UUID> gameService;
    private final CommonService<UploadedGame, UUID> uploadedGameService;
    private final CommonEnumerationService<Genre, Integer> genreService;
    private final CommonService<Requirements, Integer> requirementsService;

    @Autowired
    public UploadedGamesController(CommonService<User, UUID> userService,
                                   CommonService<UploadedGame, UUID> gameService,
                                   CommonService<UploadedGame, UUID> uploadedGameService,
                                   CommonEnumerationService<Genre, Integer> genreService,
                                   CommonService<Requirements, Integer> requirementsService) {
        this.gameService = gameService;
        this.userService = userService;
        this.uploadedGameService = uploadedGameService;
        this.genreService = genreService;
        this.requirementsService = requirementsService;
    }

    @GetMapping
    public String uploadedGamesPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = ((UserDetailsService) userService).get(name);
        model.addAttribute("uploadedGames", gameService.getAll(user.getId()));

        return "uploaded-games";
    }


    @GetMapping("/uploaded-games/edit/{id}")
    public String uploadedGamePage(@ModelAttribute UploadInput uploadInput,
                                   BindingResult bindingResult,
                                   @PathVariable UUID id,
                                   Model model) {
        UploadedGame uploadedGame = uploadedGameService.get(id);
        model.addAttribute("uploadedGame", uploadedGame);
        model.addAttribute("uploadInput", new UploadInput());
        model.addAttribute("genreList", genreService.getAll());

        model.addAttribute("processors",
            ((RequirementsService) requirementsService).getProcessorNames());
        model.addAttribute("graphicCards",
            ((RequirementsService) requirementsService).getGraphicsCardNames());
        model.addAttribute("osList",
            ((RequirementsService) requirementsService).getOSNames());

        return "edit-game";
    }
}
