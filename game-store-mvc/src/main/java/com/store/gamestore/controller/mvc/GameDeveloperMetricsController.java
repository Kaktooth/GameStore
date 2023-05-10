package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.service.game.GameService;
import com.store.gamestore.service.metrics.MetricsService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game-metrics")
@RequiredArgsConstructor
public class GameDeveloperMetricsController {

  private final MetricsService metricsService;
  private final GameService gameService;
  private final UserHolder userHolder;

  @GetMapping
  public String getGameMetricsPage(Model model) {
    var user = userHolder.getAuthenticated();
    var gameMetrics = metricsService.getCalculatedGamesMetrics(user.getId());

    addChartData(model);
    model.addAttribute("user", user);
    model.addAttribute("selectedGame", gameMetrics.entrySet().iterator().next().getKey());
    model.addAttribute("gameMetrics", gameMetrics);
    return "game-metrics";
  }

  @GetMapping("/{gameId}")
  public String getMetricsForGameId(Model model, @PathVariable UUID gameId) {
    var user = userHolder.getAuthenticated();
    var gameMetrics = metricsService.getCalculatedGamesMetrics(user.getId());

    addChartData(model);
    model.addAttribute("user", user);
    model.addAttribute("selectedGame", gameService.get(gameId));
    model.addAttribute("gameMetrics", gameMetrics);
    return "game-metrics";
  }

  private void addChartData(Model model) {
    Map<String, Integer> graphData = new TreeMap<>();
    var localDate = LocalDate.now();
    graphData.put(localDate.minusMonths(12).toString(), 33);
    graphData.put(localDate.minusMonths(11).toString(), 75);
    graphData.put(localDate.minusMonths(10).toString(), 46);
    graphData.put(localDate.minusMonths(9).toString(), 54);
    graphData.put(localDate.minusMonths(8).toString(), 59);
    graphData.put(localDate.minusMonths(7).toString(), 43);
    graphData.put(localDate.minusMonths(6).toString(), 27);
    graphData.put(localDate.minusMonths(5).toString(), 65);
    graphData.put(localDate.minusMonths(4).toString(), 31);
    graphData.put(localDate.minusMonths(3).toString(), 62);
    graphData.put(localDate.minusMonths(2).toString(), 23);
    graphData.put(localDate.minusMonths(1).toString(), 43);
    graphData.put(localDate.toString(), 22);
    model.addAttribute("chartData", graphData);
  }
}