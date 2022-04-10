package com.store.gamestore.model.entity;

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
public class ConvertedRequirements {
    private Integer id;
    private Integer minimalMemory;
    private Integer recommendedMemory;
    private Integer minimalStorage;
    private Integer recommendedStorage;
    private GameProfile gameProfile;
    private Processor minimalProcessor;
    private Processor recommendedProcessor;
    private GraphicsCard minimalGraphicCard;
    private GraphicsCard recommendedGraphicCard;
    private OperatingSystem minimalOperatingSystem;
    private OperatingSystem recommendedOperatingSystem;
}
