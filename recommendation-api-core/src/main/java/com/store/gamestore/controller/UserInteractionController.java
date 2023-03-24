package com.store.gamestore.controller;

import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interaction")
public class UserInteractionController {

  private final UserInteractionRepository userInteractionRepository;

  @PostMapping("/save")
  void saveInteraction(@RequestBody UserInteraction userInteraction) {
    userInteractionRepository.save(userInteraction);
  }
}
