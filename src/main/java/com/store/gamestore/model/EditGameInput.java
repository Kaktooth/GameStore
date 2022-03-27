package com.store.gamestore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class EditGameInput {

    public EditGameInput(UploadedGame game, Requirements requirements) {
        this.setPrice(game.getGame().getGameProfile().getPrice());
        this.setName(game.getGame().getGameProfile().getName());
        this.setDeveloper(game.getGame().getGameProfile().getDeveloper());
        this.setPublisher(game.getGame().getGameProfile().getPublisher());
        this.setDescription(game.getGame().getGameProfile().getDescription());
        this.setSmallDescription(game.getGame().getGameProfile().getBriefDescription());
        this.setRelease(game.getGame().getGameProfile().getReleaseDate().toString());
        this.setMinMemory(requirements.getMinimalMemory());
        this.setMinStorage(requirements.getMinimalStorage());
        this.setRecMemory(requirements.getRecommendedMemory());
        this.setRecStorage(requirements.getRecommendedStorage());
        this.setMinProcessorId(requirements.getMinimalProcessorId());
        this.setMinGraphicCardId(requirements.getMinimalGraphicCardId());
        this.setMinOSId(requirements.getMinimalOperatingSystemId());
        this.setRecProcessorId(requirements.getRecommendedProcessorId());
        this.setRecGraphicCardId(requirements.getRecommendedGraphicCardId());
        this.setRecOSId(requirements.getRecommendedOperatingSystemId());
        List<Integer> genres = new ArrayList<>();
        for (Genre genre : game.getGame().getGenre().getGenres()) {
            genres.add(genre.getId());
        }
        this.setGenres(genres);
    }

    private BigDecimal price;
    private String name;
    private String developer;
    private String publisher;
    private String description;
    private String smallDescription;
    private String release;
    private Integer minMemory;
    private Integer minStorage;
    private Integer recMemory;
    private Integer recStorage;
    private Integer minProcessorId;
    private Integer minGraphicCardId;
    private Integer minOSId;
    private Integer recProcessorId;
    private Integer recGraphicCardId;
    private Integer recOSId;
    private List<Integer> genres;
}
