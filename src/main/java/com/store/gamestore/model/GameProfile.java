package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameProfile {
    private Integer id;
    private BigDecimal price;
    private String name;
    private String developer;
    private String publisher;
    private Integer rating;
    private LocalDateTime releaseDate;
    private String description;
    private String briefDescription;
    private Integer gameId;
}
