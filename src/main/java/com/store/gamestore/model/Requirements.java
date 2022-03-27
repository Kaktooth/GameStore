package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Requirements {
    private Integer id;
    private Integer minimalMemory;
    private Integer recommendedMemory;
    private Integer minimalStorage;
    private Integer recommendedStorage;
    private Integer gameProfileId;
    private Integer minimalProcessorId;
    private Integer recommendedProcessorId;
    private Integer minimalGraphicCardId;
    private Integer recommendedGraphicCardId;
    private Integer minimalOperatingSystemId;
    private Integer recommendedOperatingSystemId;
}
