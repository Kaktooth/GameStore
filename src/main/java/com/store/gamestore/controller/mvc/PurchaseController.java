package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.GamePurchase;
import com.store.gamestore.persistence.entity.UserGame;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.game.purchase.PurchaseHistoryService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

  private final UserHolder userHolder;
  private final GamePictureService gameImageService;
  private final CommonService<UserGame, UUID> userGamesRepository;
  private final CommonService<Game, UUID> gameService;
  private final PurchaseHistoryService purchaseHistoryService;

  @GetMapping("/{gameId}")
  public String getPurchasePage(@PathVariable UUID gameId, Model model) {

    model.addAttribute("user", userHolder.getAuthenticated());
    var game = gameService.get(gameId);
    var gameImage = gameImageService.findGamePictureByGameIdAndPictureTypeId(gameId,
        GamePictureType.GAME_PAGE.ordinal());
    model.addAttribute("game", game);
    model.addAttribute("gameImage", gameImage);

    return "purchase";
  }

  @PostMapping("/{gameId}")
  public String purchaseGame(@PathVariable UUID gameId, Model model) {

    var user = userHolder.getAuthenticated();
    model.addAttribute("user", user);

    var game = gameService.get(gameId);
    var userGame = new UserGame(user.getId(), game);
    userGamesRepository.save(userGame);
    var gamePurchase = new GamePurchase(game.getPrice(), LocalDateTime.now().toLocalDate(),
        user.getId(), game);
    purchaseHistoryService.save(gamePurchase);

    return "redirect:/collection";
  }

  @GetMapping("/history")
  public String getPurchaseHistoryPage(Model model) {
    var user = userHolder.getAuthenticated();
    model.addAttribute("user", user);
    var gamePurchaseList = purchaseHistoryService.findAllByUserId(user.getId());
    model.addAttribute("gamePurchaseList", gamePurchaseList);

    return "purchase-history";
  }

  @PostMapping("/{gameId}/get-free")
  public String getFreeGame(@PathVariable UUID gameId) {

    var user = userHolder.getAuthenticated();
    var game = gameService.get(gameId);
    var userGame = new UserGame(user.getId(), game);
    userGamesRepository.save(userGame);

    return "redirect:/collection";
  }
}
