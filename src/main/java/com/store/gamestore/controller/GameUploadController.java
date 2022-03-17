package com.store.gamestore.controller;

import com.store.gamestore.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GameUploadController {

    private final GameService gameServiceImpl;

    @Autowired
    public GameUploadController(GameService gameServiceImpl) {
        this.gameServiceImpl = gameServiceImpl;
    }

    @GetMapping("/upload")
    public String homepage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        gameServiceImpl.save(file);

        attributes.addFlashAttribute("message", "Your file successfully uploaded " + file.getOriginalFilename() + '!');

        return "redirect:/";
    }

}

