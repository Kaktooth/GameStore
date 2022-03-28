package com.store.gamestore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class UploadInput {

    private BigDecimal price;
    @Size(message = "Invalid size, game title must be from 1200 to 6000 characters",
        min = 1200, max = 6000)
    private String title;
    private String developer;
    private String publisher;
    @Size(message = "Invalid size, description must be from 1200 to 6000 characters",
        min = 1200, max = 6000)
    private String description;
    @Size(message = "Invalid size, description must be from 50 to 200 characters",
        min = 50, max = 200)
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
    private String version;
}
