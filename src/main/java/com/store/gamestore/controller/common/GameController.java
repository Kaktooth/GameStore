package com.store.gamestore.controller.common;

import com.store.gamestore.model.entity.ConvertedRequirements;
import com.store.gamestore.model.entity.FavoriteGame;
import com.store.gamestore.model.entity.GameImage;
import com.store.gamestore.model.entity.GraphicsCard;
import com.store.gamestore.model.entity.OperatingSystem;
import com.store.gamestore.model.entity.PictureType;
import com.store.gamestore.model.entity.Processor;
import com.store.gamestore.model.entity.Requirements;
import com.store.gamestore.model.entity.UploadedGame;
import com.store.gamestore.model.entity.User;
import com.store.gamestore.model.entity.UserGame;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.counting.CounterService;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import com.store.gamestore.service.user.UserDetailsService;
import com.store.gamestore.model.util.GamePicturesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/game/{id}")
public class GameController {

    private final CommonService<User, UUID> userService;
    private final CommonService<GameImage, UUID> gameImageService;
    private final CommonService<UploadedGame, UUID> uploadedGameService;
    private final CommonService<FavoriteGame, UUID> favoriteGameService;
    private final CommonService<Requirements, Integer> requirementsService;
    private final CommonEnumerationService<Processor, Integer> processorService;
    private final CommonService<UserGame, UUID> userGamesService;
    private final CounterService<UUID> favoriteCounterService;
    private final CounterService<UUID> viewsCounterService;
    private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
    private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;

    @Autowired
    public GameController(CommonService<User, UUID> userService,
                          CommonService<UserGame, UUID> userGamesService,
                          CommonService<GameImage, UUID> gameImageService,
                          @Qualifier("gameFavoriteCounterService")
                              CounterService<UUID> favoriteCounterService,
                          @Qualifier("gameViewsCounterService")
                              CounterService<UUID> viewsCounterService,
                          @Qualifier("uploadedGameService")
                              CommonService<UploadedGame, UUID> uploadedGameService,
                          CommonService<FavoriteGame, UUID> favoriteGameService,
                          CommonService<Requirements, Integer> requirementsService,
                          CommonEnumerationService<Processor, Integer> processorService,
                          CommonEnumerationService<GraphicsCard, Integer> graphicsCardService,
                          CommonEnumerationService<OperatingSystem, Integer> operatingSystemService) {

        this.userService = userService;
        this.userGamesService = userGamesService;
        this.gameImageService = gameImageService;
        this.favoriteCounterService = favoriteCounterService;
        this.viewsCounterService = viewsCounterService;
        this.uploadedGameService = uploadedGameService;
        this.favoriteGameService = favoriteGameService;
        this.requirementsService = requirementsService;
        this.processorService = processorService;
        this.graphicsCardService = graphicsCardService;
        this.operatingSystemService = operatingSystemService;
    }

    @PostMapping("/add-favorite")
    public String addToFavorite(@PathVariable String id,
                                Model model) {

        UUID gameId = UUID.fromString(id);
        UploadedGame uploadedGame = uploadedGameService.get(gameId);
        User user = getUser();
        FavoriteGame favoriteGame = new FavoriteGame(user, uploadedGame.getGame());
        favoriteGameService.save(favoriteGame);
        favoriteCounterService.count(gameId);
        getGamePage(id, model);

        return "game";
    }

    @PostMapping("/remove-favorite")
    public String deleteFavorite(@PathVariable String id,
                                 Model model) {

        UUID gameId = UUID.fromString(id);
        favoriteGameService.delete(gameId);
        favoriteCounterService.decreaseCount(gameId);
        getGamePage(id, model);

        return "game";
    }


    @GetMapping
    public String getGamePage(@PathVariable("id") String id,
                              Model model) {

        UUID gameId = UUID.fromString(id);
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

        User user = getUser();
        model.addAttribute("user", user);

        List<FavoriteGame> favoriteGameList = favoriteGameService.getAll(user.getId());

        boolean favorite = false;
        for (FavoriteGame favoriteGame : favoriteGameList) {
            if (favoriteGame.getGame().getId().toString().equals(id)) {
                favorite = true;
                break;
            }
        }

        List<GameImage> gameImages = gameImageService.getAll(gameId);

        GameImage gamePageImage = GamePicturesUtil.getGamePicture(gameImages, PictureType.GAMEPAGE);
        model.addAttribute("gamePagePicture", gamePageImage);

        List<GameImage> gameplayPictures = GamePicturesUtil.getGameplayPictures(gameImages);
        model.addAttribute("gameplayPictures", gameplayPictures);

        List<UserGame> userGames = userGamesService.getAll(user.getId());
        boolean purchased = false;
        for (UserGame userGame : userGames) {
            if (userGame.getGame().getId().equals(uploadedGame.getGame().getId())) {
                purchased = true;
                break;
            }
        }
        model.addAttribute("purchased", purchased);
        model.addAttribute("favorite", favorite);
        model.addAttribute("uploadedGame", uploadedGame);
        model.addAttribute("requirements", convertedRequirements);

        viewsCounterService.count(gameId);

        return "game";
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return ((UserDetailsService) userService).get(name);
    }
}