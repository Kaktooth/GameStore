package com.store.gamestore.controller.mvc;

import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.consumer.KafkaLatestRecordConsumer;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.service.game.GameService;
import com.store.gamestore.service.metrics.MetricsService;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/game-metrics")
@RequiredArgsConstructor
public class GameDeveloperController {

  private final KafkaLatestRecordConsumer<HashMap<UUID, List<UserInteraction>>> gameInteractionsConsumer;
  private final MetricsService metricsService;
  private final GameService gameService;
  private final UserHolder userHolder;

  @GetMapping
  public String getGameMetricsPage(Model model, @RequestParam(required = false) String chartName) {
    var user = userHolder.getAuthenticated();
    var gameMetrics = metricsService.getCalculatedGamesMetrics(user.getId());
    var game = gameMetrics.entrySet().iterator().next().getKey();

    addChartData(model, game.getId(), chartName);
    model.addAttribute("user", user);
    model.addAttribute("selectedGame", game);
    model.addAttribute("gameMetrics", gameMetrics);
    return "game-metrics";
  }

  @GetMapping("/{gameId}")
  public String getMetricsForGameId(Model model, @PathVariable UUID gameId,
      @RequestParam(required = false) String chartName) {
    var user = userHolder.getAuthenticated();
    var gameMetrics = metricsService.getCalculatedGamesMetrics(user.getId());

    addChartData(model, gameId, chartName);
    model.addAttribute("user", user);
    model.addAttribute("selectedGame", gameService.get(gameId));
    model.addAttribute("gameMetrics", gameMetrics);
    return "game-metrics";
  }

  private void addChartData(Model model, UUID gameId, String chartName) {
    var gameInteractions = gameInteractionsConsumer.getRecord(
        KafkaTopics.USER_INTERACTION_METRICS_ID).get(gameId);
    var interactionData = new HashMap<InteractionType, HashMap<String, Integer>>();
    Arrays.stream(InteractionType.values()).forEach(interactionType -> {
      var gameInteractionsCountedByMonth = new HashMap<String, Integer>();
      var currentDate = LocalDate.now();
      for (int i = 0; i < Month.values().length; i++) {
        var newDate = currentDate.minusMonths(i);
        var formattedNewDate = newDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        gameInteractionsCountedByMonth.put(formattedNewDate, 0);
      }
      gameInteractions.stream()
          .filter(interaction -> interaction.getInteractionType().equals(interactionType))
          .forEach(interaction -> {
            var date = interaction.getDate().toLocalDate();

            var month = date.getMonth();
            var startDate = date.withDayOfMonth(1);
            var endDate = date.withDayOfMonth(month.maxLength());
            if (date.isAfter(startDate) && date.isBefore(endDate)) {
              var formattedDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
              var usedDate = gameInteractionsCountedByMonth.get(formattedDate);
              if (usedDate != null) {
                var countedInteraction = usedDate + 1;
                gameInteractionsCountedByMonth.put(formattedDate, countedInteraction);
              }
            }
          });
      interactionData.put(interactionType, gameInteractionsCountedByMonth);
    });

//    var interaction = (String) model.getAttribute("chart");
    if (chartName == null) {
      chartName = InteractionType.VISITED.name();
    }
    var chartData = interactionData.get(InteractionType.valueOf(chartName));

    var capitalizedInteractionName = StringUtils.capitalize(chartName.toLowerCase());
    model.addAttribute("interaction", capitalizedInteractionName);
    model.addAttribute("chartData", chartData);
  }
}