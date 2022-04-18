package com.launcher.launcher.model.entity;

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
    public Integer id;
    public BigDecimal price;
    public String title;
    public String developer;
    public String publisher;
    public Integer rating;
    public Integer views;
    public Integer purchase;
    public Integer favorite;
    public LocalDateTime releaseDate;
    public String description;
    public String briefDescription;
    public UUID gameId;

    public Integer getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }
}
