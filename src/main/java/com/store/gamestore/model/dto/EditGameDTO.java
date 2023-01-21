package com.store.gamestore.model.dto;

import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GameProfile;
import com.store.gamestore.persistence.entity.Genre;
import com.store.gamestore.persistence.entity.SystemRequirements;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditGameDTO {

  public EditGameDTO(Game game, GameProfile gameProfile, SystemRequirements requirements) {
    this.setPrice(game.getPrice());
    this.setTitle(game.getTitle());
    this.setDeveloper(game.getDeveloper());
    this.setPublisher(game.getPublisher());
    this.setDescription(gameProfile.getDescription());
    this.setSmallDescription(gameProfile.getBriefDescription());
    this.setRelease(gameProfile.getReleaseDate().toString());
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

    List<Integer> genres = game.getGenres()
        .stream()
        .map(Genre::getId)
        .toList();
    this.setGenres(genres);
  }


  private BigDecimal price;
  private String title;
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
