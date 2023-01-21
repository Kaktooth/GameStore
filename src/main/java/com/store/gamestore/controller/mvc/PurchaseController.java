package com.store.gamestore.controller.mvc;

import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.GamePurchase;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.persistence.entity.UserGame;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.game.purchase.PurchaseHistoryService;
import com.store.gamestore.service.user.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  private final UserService userService;
  private final GamePictureService gameImageService;
  private final CommonService<UserGame, UUID> userGamesRepository;
  private final CommonService<Game, UUID> gameService;
  private final PurchaseHistoryService purchaseHistoryService;

  @GetMapping("/{gameId}")
  public String getPurchasePage(@PathVariable UUID gameId, Model model) {

    User user = getUser();
    model.addAttribute("user", user);
    Game game = gameService.get(gameId);

    GamePicture gameImage = gameImageService.findGamePictureByGameIdAndPictureTypeId(gameId,
        GamePictureType.GAME_PAGE.ordinal());
    model.addAttribute("game", game);
    model.addAttribute("gameImage", gameImage);

    return "purchase";
  }

  @PostMapping("/{gameId}")
  public String purchaseGame(@PathVariable UUID gameId, Model model) {

    User user = getUser();
    model.addAttribute("user", user);

    Game game = gameService.get(gameId);
    UserGame userGame = new UserGame(user.getId(), game);
    userGamesRepository.save(userGame);
    GamePurchase gamePurchase = new GamePurchase(game.getPrice(),
        LocalDateTime.now().toLocalDate(), user.getId(), game);
    purchaseHistoryService.save(gamePurchase);

    return "redirect:/collection";
  }

  @GetMapping("/history")
  public String getPurchaseHistoryPage(Model model) {
    User user = getUser();
    model.addAttribute("user", user);
    List<GamePurchase> gamePurchaseList = purchaseHistoryService.findAllByUserId(user.getId());
    model.addAttribute("gamePurchaseList", gamePurchaseList);

    return "purchase-history";
  }

  @PostMapping("/{gameId}/get-free")
  public String getFreeGame(@PathVariable UUID gameId) {

    User user = getUser();
    Game game = gameService.get(gameId);
    UserGame userGame = new UserGame(user.getId(), game);
    userGamesRepository.save(userGame);

    return "redirect:/collection";
  }

  private User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    return userService.findUserByUsername(name);
  }
}
