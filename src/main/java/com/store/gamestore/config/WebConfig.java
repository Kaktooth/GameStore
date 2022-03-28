package com.store.gamestore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/store");
        registry.addViewController("/upload/**").setViewName("upload");
        registry.addViewController("/uploaded-games/**").setViewName("uploaded-games");
        registry.addViewController("/game/**").setViewName("game");
        registry.addViewController("/store/**").setViewName("store");
        registry.addViewController("/profile/**").setViewName("profile");
        registry.addViewController("/log-in/**").setViewName("log-in");
        registry.addViewController("/create_account/**").setViewName("create-account");
        registry.addViewController("/account/**").setViewName("account");
        registry.addViewController("/collection/**").setViewName("collection");
    }
}
