package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.dto.GameDTO;
import com.store.gamestore.model.util.GameMapper;
import com.store.gamestore.model.util.Pagination;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.StoreBanner;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.search.SearchService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

  private final CommonService<Game, UUID> gameService;
  private final GameMapper gameMapper;
  private final UserHolder userHolder;
  private final SearchService<GameDTO> gameSearchService;
  private final CommonService<StoreBanner, UUID> storeBannerService;

  //  TODO add top recommended games
  @GetMapping
  public String getStorePage(
      @RequestParam(value = "searchString", required = false) String searchString, Model model) {

    var authenticatedUser = userHolder.getAuthenticated();
    if (authenticatedUser != null) {
      model.addAttribute("user", authenticatedUser);
    }

    int size = 4;
    int searchRange = 4;
    var storeBanners = storeBannerService.getAll();
    model.addAttribute("bannerItems", storeBanners);

    var popularGames = gameService.getAll();
    var popularGamesDtoList = gameMapper.sourceToDestination(popularGames);
    var pagination = new Pagination<>(popularGamesDtoList);
    var popularGamesMap = pagination.toMap(size, pagination.getPageCount(size));

    model.addAttribute("popularGamesMap", popularGamesMap);
    model.addAttribute("bestSellerGamesMap", popularGamesMap);
    model.addAttribute("mostFavoriteGamesMap", popularGamesMap);

    if (searchString == null || searchString.equals("")) {
      model.addAttribute("search", false);
    } else {
      var searchedGames = gameSearchService.searchGames(searchString, searchRange);
      model.addAttribute("search", true);
      model.addAttribute("searchedGames", searchedGames);
    }

    return "store";
  }
}
