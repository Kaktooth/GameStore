package com.store.gamestore.controller;

import com.store.gamestore.model.GameImage;
import com.store.gamestore.model.PictureType;
import com.store.gamestore.model.StoreBannerItem;
import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.model.UploadedGameDto;
import com.store.gamestore.model.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.recommended.Recommendations;
import com.store.gamestore.service.game.search.GameSearcher;
import com.store.gamestore.service.user.UserDetailsService;
import com.store.gamestore.util.GamePicturesUtil;
import com.store.gamestore.util.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/store")
public class StoreController {
    private final CommonService<User, UUID> userService;
    private final GameSearcher<UploadedGame> gameSearchService;
    private final CommonService<GameImage, UUID> gameImageService;
    private final Recommendations<UploadedGame> gameRecommendationService;
    private final CommonService<StoreBannerItem, UUID> storeBannerService;

    @Autowired
    public StoreController(CommonService<User, UUID> userService,
                           GameSearcher<UploadedGame> gameSearchService,
                           CommonService<GameImage, UUID> gameImageService,
                           CommonService<StoreBannerItem, UUID> storeBannerService,
                           @Qualifier("gameRecommendationService") Recommendations<UploadedGame> gameRecommendationService) {
        this.userService = userService;
        this.gameSearchService = gameSearchService;
        this.gameImageService = gameImageService;
        this.storeBannerService = storeBannerService;
        this.gameRecommendationService = gameRecommendationService;
    }

    @GetMapping
    public String getStorePage(@RequestParam(value = "searchString", required = false) String searchString,
                               Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        if (!name.equals("anonymousUser")) {

            User user = ((UserDetailsService) userService).get(name);
            model.addAttribute("user", user);
        }

        Integer size = 3;
        Integer pages = 4;
        Integer itemsCount = size * pages;
        Integer searchRange = 5;
        List<StoreBannerItem> bannerItems = storeBannerService.getAll();
        model.addAttribute("bannerItems", bannerItems);

        List<UploadedGame> popularGames = gameRecommendationService.getPopularGames(itemsCount);
        List<UploadedGameDto> popularGamesDto = new ArrayList<>();
        for (var game : popularGames) {
            List<GameImage> gameImages = gameImageService.getAll(game.getGame().getId());
            GameImage gameImage = GamePicturesUtil.getGamePicture(gameImages,
                PictureType.STORE);
            popularGamesDto.add(new UploadedGameDto(game, gameImage));
        }
        Pagination<UploadedGameDto> pagination = new Pagination<>(popularGamesDto);
        Map<Integer, List<UploadedGameDto>> popularGamesMap = pagination.toMap(size, pagination.getPageCount(size));

        List<UploadedGame> bestSellerGames = gameRecommendationService.getBestSellerGames(itemsCount);
        List<UploadedGameDto> bestSellerGamesDto = new ArrayList<>();
        for (var game : bestSellerGames) {
            List<GameImage> gameImages = gameImageService.getAll(game.getGame().getId());
            GameImage gameImage = GamePicturesUtil.getGamePicture(gameImages,
                PictureType.STORE);
            bestSellerGamesDto.add(new UploadedGameDto(game, gameImage));
        }
        pagination = new Pagination<>(bestSellerGamesDto);
        Map<Integer, List<UploadedGameDto>> bestSellerGamesMap = pagination.toMap(size, pagination.getPageCount(size));
        List<UploadedGame> mostFavoriteGames = gameRecommendationService.getMostFavoriteGames(itemsCount);
        List<UploadedGameDto> mostFavoriteGamesDto = new ArrayList<>();
        for (var game : mostFavoriteGames) {
            List<GameImage> gameImages = gameImageService.getAll(game.getGame().getId());
            GameImage gameImage = GamePicturesUtil.getGamePicture(gameImages,
                PictureType.STORE);
            mostFavoriteGamesDto.add(new UploadedGameDto(game, gameImage));
        }
        pagination = new Pagination<>(mostFavoriteGamesDto);
        Map<Integer, List<UploadedGameDto>> mostFavoriteGamesMap = pagination.toMap(size, pagination.getPageCount(size));
        model.addAttribute("popularGamesMap", popularGamesMap);
        model.addAttribute("bestSellerGamesMap", bestSellerGamesMap);
        model.addAttribute("mostFavoriteGamesMap", mostFavoriteGamesMap);

        if (searchString == null || searchString.equals("")) {
            model.addAttribute("search", false);
        } else {
            List<UploadedGame> searchedGames = gameSearchService.searchGames(searchString, searchRange);
            List<UploadedGameDto> searchedGamesDto = new ArrayList<>();
            for (var game : searchedGames) {
                List<GameImage> gameImages = gameImageService.getAll(game.getGame().getId());
                GameImage gameImage = GamePicturesUtil.getGamePicture(gameImages,
                    PictureType.GAMEPAGE);
                searchedGamesDto.add(new UploadedGameDto(game, gameImage));
            }

            model.addAttribute("search", true);
            model.addAttribute("searchedGames", searchedGamesDto);
        }

        return "store";
    }
}
