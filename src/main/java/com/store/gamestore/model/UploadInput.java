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
public class UploadInput {

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
    private String version;
}
