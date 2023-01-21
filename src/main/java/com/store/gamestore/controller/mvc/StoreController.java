package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.dto.GameDTO;
import com.store.gamestore.model.util.GameMapper;
import com.store.gamestore.model.util.Pagination;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.StoreBanner;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.search.SearchService;
import com.store.gamestore.service.user.UserService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  private final UserService userService;
  private final SearchService<GameDTO> gameSearchService;
  private final GamePictureService gameImageService;
  private final CommonService<StoreBanner, UUID> storeBannerService;

  //  TODO add top recommended games
  @GetMapping
  public String getStorePage(
      @RequestParam(value = "searchString", required = false) String searchString, Model model) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    if (!name.equals("anonymousUser")) {
      User user = userService.findUserByUsername(name);
      model.addAttribute("user", user);
    }

    int size = 4;
    Integer searchRange = 4;
    List<StoreBanner> storeBanners = storeBannerService.getAll();
    model.addAttribute("bannerItems", storeBanners);

    List<Game> popularGames = gameService.getAll();
    List<GameDTO> popularGamesDtoList = gameMapper.sourceToDestination(popularGames);
    Pagination<GameDTO> pagination = new Pagination<>(popularGamesDtoList);
    Map<Integer, List<GameDTO>> popularGamesMap = pagination.toMap(size,
        pagination.getPageCount(size));

    model.addAttribute("popularGamesMap", popularGamesMap);
    model.addAttribute("bestSellerGamesMap", popularGamesMap);
    model.addAttribute("mostFavoriteGamesMap", popularGamesMap);

    if (searchString == null || searchString.equals("")) {
      model.addAttribute("search", false);
    } else {
      List<GameDTO> searchedGames = gameSearchService.searchGames(searchString, searchRange);

      model.addAttribute("search", true);
      model.addAttribute("searchedGames", searchedGames);
    }

    return "store";
  }
}
