package com.store.gamestore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/account/**").setViewName("account");
//        registry.addViewController("/profile/**").setViewName("profile");
        registry.addRedirectViewController("/", "/?store");
        registry.addViewController("/upload/**").setViewName("upload");
        registry.addViewController("/**").setViewName("layout");
    }
}
