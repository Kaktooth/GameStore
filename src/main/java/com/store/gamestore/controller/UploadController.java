package com.store.gamestore.controller;

import com.store.gamestore.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Controller
public class UploadController {

    private final GameServiceImpl gameServiceImpl;
    private final String UPLOAD_DIR = "upload/";

    @Autowired
    public UploadController(GameServiceImpl gameServiceImpl) {
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

        // to-do
        // create new class to create file and delete

        String fileName = file.getOriginalFilename();
        File newGameFile = new File(UPLOAD_DIR + fileName);

        try {
            Files.copy(file.getInputStream(), newGameFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameServiceImpl.save(newGameFile);

        try {
            Files.delete(newGameFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/";
    }

}

