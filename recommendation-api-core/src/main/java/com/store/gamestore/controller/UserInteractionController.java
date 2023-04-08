package com.store.gamestore.controller;

import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import com.store.gamestore.service.UserInteractionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interaction")
public class UserInteractionController {

  private final UserInteractionsService userInteractionsService;

  @PostMapping("/save")
  void saveInteraction(@RequestBody UserInteraction userInteraction) {
    userInteractionsService.save(userInteraction);
  }
}
