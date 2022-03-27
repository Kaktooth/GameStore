package com.store.gamestore.controller;

import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/game")
public class GameController {

    private final CommonService<UploadedGame, UUID> uploadedGameService;

    @Autowired
    public GameController(CommonService<UploadedGame, UUID> uploadedGameService) {
        this.uploadedGameService = uploadedGameService;
    }

    @GetMapping("/{id}")
    public String getGamePage(@PathVariable("id") String id,
                              Model model) {
        UploadedGame uploadedGame = uploadedGameService.get(UUID.fromString(id));
        model.addAttribute("uploadGame", uploadedGame);
        return "game";
    }
}
