package com.store.gamestore.controller.mvc;

import com.store.gamestore.common.AppConstraints;
import com.store.gamestore.common.AppConstraints.Search;
import com.store.gamestore.common.Pagination;
import com.store.gamestore.common.mapper.UserRecommendationMapper;
import com.store.gamestore.model.dto.GameDTO;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.service.recommendation.UserRecommendationService;
import com.store.gamestore.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/store/recommendations")
@RequiredArgsConstructor
public class RecommendationsController {

  private final UserHolder userHolder;
  private final UserRecommendationMapper userRecommendationMapper;
  private final SearchService<GameDTO> gameSearchService;
  private final UserRecommendationService userRecommendationService;

  @GetMapping
  public String getStorePage(
      @RequestParam(value = "searchString", required = false) String searchString, Model model) {

    var authenticatedUser = userHolder.getAuthenticated();
    if (authenticatedUser != null) {
      model.addAttribute("user", authenticatedUser);
    }

    var userRecommendationsDTO = userRecommendationMapper.sourceToDestination(
        userRecommendationService.getRecommendations());
    var pagination = new Pagination<>(userRecommendationsDTO);
    var pageLength = pagination.getPageCount(AppConstraints.Pagination.PAGE_SIZE);
    var recommendationMap = pagination.toMap(AppConstraints.Pagination.PAGE_SIZE, pageLength);
    model.addAttribute("recommendationMap", recommendationMap);

    if (searchString == null || searchString.equals("")) {
      model.addAttribute("search", false);
    } else {
      var searchedGames = gameSearchService.searchGames(searchString, Search.RANGE);
      model.addAttribute("search", true);
      model.addAttribute("searchedGames", searchedGames);
    }

    return "recommendations";
  }
}
