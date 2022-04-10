package com.store.gamestore.controller.common;

import com.store.gamestore.model.entity.EditGameInput;
import com.store.gamestore.model.entity.GameplayImagesDTO;
import com.store.gamestore.model.entity.Genre;
import com.store.gamestore.model.entity.GraphicsCard;
import com.store.gamestore.model.entity.OperatingSystem;
import com.store.gamestore.model.entity.Processor;
import com.store.gamestore.model.entity.Requirements;
import com.store.gamestore.model.entity.UploadedGame;
import com.store.gamestore.model.entity.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import com.store.gamestore.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequestMapping("edit/{id}/images")
public class ImagesController {
    private final CommonService<User, UUID> userService;
    private final CommonEnumerationService<Genre, Integer> genreService;
    private final CommonEnumerationService<Processor, Integer> processorService;
    private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
    private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;
    private final CommonService<UploadedGame, UUID> uploadedGameService;
    private final CommonService<Requirements, Integer> requirementsService;

    public ImagesController(CommonService<User, UUID> userService,
                            CommonEnumerationService<Genre, Integer> genreService,
                            CommonEnumerationService<Processor, Integer> processorService,
                            CommonEnumerationService<GraphicsCard, Integer> graphicsCardService,
                            CommonEnumerationService<OperatingSystem, Integer> operatingSystemService,
                            @Qualifier("uploadedGameService")
                            CommonService<UploadedGame, UUID> uploadedGameService,
                            CommonService<Requirements, Integer> requirementsService) {
        this.userService = userService;
        this.genreService = genreService;
        this.processorService = processorService;
        this.graphicsCardService = graphicsCardService;
        this.operatingSystemService = operatingSystemService;
        this.uploadedGameService = uploadedGameService;
        this.requirementsService = requirementsService;
    }

    @PostMapping("/add")
    public String addImage(@PathVariable("id") String id,
                           @ModelAttribute("gameImages") GameplayImagesDTO gameImages,
                           @RequestParam("uploadedImage") MultipartFile image,
                           Model model) {


        User user = getUser();
        model.addAttribute("user", user);

        UUID gameId = UUID.fromString(id);
        UploadedGame uploadedGame = uploadedGameService.get(gameId);
        model.addAttribute("uploadedGame", uploadedGame);
        model.addAttribute("genreList", genreService.getAll());
        model.addAttribute("processors", processorService.getAll());
        model.addAttribute("graphicCards", graphicsCardService.getAll());
        model.addAttribute("osList", operatingSystemService.getAll());

        Integer gameProfileId = uploadedGame.getGame().getGameProfile().getId();

        Requirements requirements = requirementsService.get(gameProfileId);

        model.addAttribute("editGameInput", new EditGameInput(uploadedGame, requirements));

        return "upload";
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return ((UserDetailsService) userService).get(name);
    }
}
