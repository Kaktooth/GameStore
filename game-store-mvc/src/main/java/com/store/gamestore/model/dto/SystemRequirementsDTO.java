package com.store.gamestore.model.dto;


import com.store.gamestore.persistence.entity.GraphicsCard;
import com.store.gamestore.persistence.entity.OperatingSystem;
import com.store.gamestore.persistence.entity.Processor;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemRequirementsDTO {

  private Integer minimalMemory;

  private Integer recommendedMemory;

  private Integer minimalStorage;

  private Integer recommendedStorage;

  private UUID gameProfileId;

  private Processor minimalProcessor;

  private Processor recommendedProcessor;

  private GraphicsCard minimalGraphicCard;

  private GraphicsCard recommendedGraphicCard;

  private OperatingSystem minimalOperatingSystem;

  private OperatingSystem recommendedOperatingSystem;

}
