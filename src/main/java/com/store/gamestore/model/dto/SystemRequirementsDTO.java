package com.store.gamestore.model.dto;


import lombok.Data;

@Data
public class SystemRequirementsDTO {

  private Long minimalMemory;

  private Long recommendedMemory;

  private Long minimalStorage;

  private Long recommendedStorage;

  private String gameProfileId;

  private Long minimalProcessorId;

  private Long recommendedProcessorId;

  private Long minimalGraphicCardId;

  private Long recommendedGraphicCardId;

  private Long minimalOperatingSystemId;

  private Long recommendedOperatingSystemId;

}
