package com.store.gamestore.controller;

import com.store.gamestore.model.StoreBannerItem;
import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.recommended.Recommendations;
import com.store.gamestore.service.game.search.GameSearcher;
import com.store.gamestore.util.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/store")
public class StoreController {

    private final GameSearcher<UploadedGame> gameSearchService;
    private final Recommendations<UploadedGame> gameRecommendationService;
    private final CommonService<StoreBannerItem, UUID> storeBannerService;

    @Autowired
    public StoreController(
        GameSearcher<UploadedGame> gameSearchService,
        CommonService<StoreBannerItem, UUID> storeBannerService,
        @Qualifier("gameRecommendationService") Recommendations<UploadedGame> gameRecommendationService) {

        this.gameSearchService = gameSearchService;
        this.storeBannerService = storeBannerService;
        this.gameRecommendationService = gameRecommendationService;
    }

    @GetMapping
    public String getStorePage(@RequestParam(value = "searchString", required = false) String searchString,
                               Model model) {

        Integer size = 3;
        Integer pages = 4;
        Integer itemsCount = size * pages;
        Integer searchRange = 5;
        List<StoreBannerItem> bannerItems = storeBannerService.getAll();
        model.addAttribute("bannerItems", bannerItems);

        List<UploadedGame> popularGames = gameRecommendationService.getPopularGames(itemsCount);
        log.info("popular: " + popularGames.toString());
        Pagination<UploadedGame> pagination = new Pagination<>(popularGames);
        Map<Integer, List<UploadedGame>> popularGamesMap = pagination.toMap(size, pagination.getPageCount(size));
        List<UploadedGame> bestSellerGames = gameRecommendationService.getBestSellerGames(itemsCount);
        pagination = new Pagination<>(bestSellerGames);
        Map<Integer, List<UploadedGame>> bestSellerGamesMap = pagination.toMap(size, pagination.getPageCount(size));
        List<UploadedGame> mostFavoriteGames = gameRecommendationService.getMostFavoriteGames(itemsCount);
        pagination = new Pagination<>(mostFavoriteGames);
        Map<Integer, List<UploadedGame>> mostFavoriteGamesMap = pagination.toMap(size, pagination.getPageCount(size));
        model.addAttribute("popularGamesMap", popularGamesMap);
        model.addAttribute("bestSellerGamesMap", bestSellerGamesMap);
        model.addAttribute("mostFavoriteGamesMap", mostFavoriteGamesMap);

        if (searchString == null || searchString.equals("")) {
            model.addAttribute("search", false);
        } else {
            List<UploadedGame> searchedGames = gameSearchService.searchGames(searchString, searchRange);
            model.addAttribute("search", true);
            model.addAttribute("searchedGames", searchedGames);
        }

        return "store";
    }
}
