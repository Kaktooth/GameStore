package com.store.gamestore.controller;

import com.store.gamestore.model.ConvertedRequirements;
import com.store.gamestore.model.FavoriteGame;
import com.store.gamestore.model.GraphicsCard;
import com.store.gamestore.model.OperatingSystem;
import com.store.gamestore.model.Processor;
import com.store.gamestore.model.Requirements;
import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/game")
public class GameController {

    private final CommonService<UploadedGame, UUID> uploadedGameService;
    private final CommonService<FavoriteGame, UUID> favoriteGameService;
    private final CommonService<Requirements, Integer> requirementsService;
    private final CommonEnumerationService<Processor, Integer> processorService;
    private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
    private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;

    @Autowired
    public GameController(@Qualifier("uploadedGameService")
                              CommonService<UploadedGame, UUID> uploadedGameService,
                          CommonService<FavoriteGame, UUID> favoriteGameService,
                          CommonService<Requirements, Integer> requirementsService,
                          CommonEnumerationService<Processor, Integer> processorService,
                          CommonEnumerationService<GraphicsCard, Integer> graphicsCardService,
                          CommonEnumerationService<OperatingSystem, Integer> operatingSystemService) {

        this.uploadedGameService = uploadedGameService;
        this.favoriteGameService = favoriteGameService;
        this.requirementsService = requirementsService;
        this.processorService = processorService;
        this.graphicsCardService = graphicsCardService;
        this.operatingSystemService = operatingSystemService;
    }

    @GetMapping("/{id}/add-favorite")
    public String gameFilesPage(@PathVariable String id,
                                Model model) {

        UUID gameId = UUID.fromString(id);
        UploadedGame uploadedGame = uploadedGameService.get(gameId);
        FavoriteGame favoriteGame = new FavoriteGame(uploadedGame.getUser(), uploadedGame.getGame());
        favoriteGameService.save(favoriteGame);
        getGamePage(id, model);

        return "game";
    }


    @GetMapping("/{id}")
    public String getGamePage(@PathVariable("id") String id,
                              Model model) {

        UploadedGame uploadedGame = uploadedGameService.get(UUID.fromString(id));
        Requirements requirements = requirementsService.get(uploadedGame.getGame().getGameProfile().getId());

        Processor minimumProcessor = processorService.get(requirements.getMinimalProcessorId());
        Processor recProcessor = processorService.get(requirements.getRecommendedProcessorId());
        GraphicsCard minimumGraphicsCard = graphicsCardService.get(requirements.getMinimalGraphicCardId());
        GraphicsCard recGraphicsCard = graphicsCardService.get(requirements.getRecommendedGraphicCardId());
        OperatingSystem minimumOS = operatingSystemService.get(requirements.getMinimalOperatingSystemId());
        OperatingSystem recOS = operatingSystemService.get(requirements.getRecommendedOperatingSystemId());
        ConvertedRequirements convertedRequirements = new ConvertedRequirements(requirements.getId(),
            requirements.getMinimalMemory(), requirements.getRecommendedMemory(), requirements.getMinimalStorage(),
            requirements.getRecommendedStorage(), uploadedGame.getGame().getGameProfile(),
            minimumProcessor, recProcessor, minimumGraphicsCard,
            recGraphicsCard, minimumOS, recOS);

        model.addAttribute("uploadedGame", uploadedGame);
        model.addAttribute("requirements", convertedRequirements);

        return "game";
    }
}
