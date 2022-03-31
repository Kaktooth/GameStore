package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameProfile {
    private Integer id;
    private BigDecimal price;
    private String title;
    private String developer;
    private String publisher;
    private Integer rating;
    private Integer views;
    private Integer purchase;
    private Integer favorite;
    private LocalDateTime releaseDate;
    private String description;
    private String briefDescription;
    private UUID gameId;
}
