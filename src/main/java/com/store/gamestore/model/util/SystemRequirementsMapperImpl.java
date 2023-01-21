package com.store.gamestore.model.util;

import com.store.gamestore.model.dto.SystemRequirementsDTO;
import com.store.gamestore.persistence.entity.GraphicsCard;
import com.store.gamestore.persistence.entity.OperatingSystem;
import com.store.gamestore.persistence.entity.Processor;
import com.store.gamestore.persistence.entity.SystemRequirements;
import com.store.gamestore.service.enumeration.CommonEnumerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemRequirementsMapperImpl implements SystemRequirementsMapper {

  private final CommonEnumerationService<Processor, Integer> processorService;
  private final CommonEnumerationService<GraphicsCard, Integer> graphicsCardService;
  private final CommonEnumerationService<OperatingSystem, Integer> operatingSystemService;

  @Override
  public SystemRequirementsDTO sourceToDestination(SystemRequirements systemRequirements) {

    return SystemRequirementsDTO.builder()
        .minimalProcessor(
            processorService.get(systemRequirements.getMinimalProcessorId()))
        .minimalGraphicCard(
            graphicsCardService.get(systemRequirements.getMinimalGraphicCardId()))
        .minimalOperatingSystem(
            operatingSystemService.get(systemRequirements.getMinimalOperatingSystemId()))
        .recommendedProcessor(
            processorService.get(systemRequirements.getRecommendedProcessorId()))
        .recommendedGraphicCard(
            graphicsCardService.get(systemRequirements.getRecommendedGraphicCardId()))
        .recommendedOperatingSystem(
            operatingSystemService.get(systemRequirements.getRecommendedOperatingSystemId()))

        .minimalMemory(systemRequirements.getMinimalMemory())
        .minimalStorage(systemRequirements.getMinimalStorage())
        .recommendedMemory(systemRequirements.getMinimalMemory())
        .recommendedStorage(systemRequirements.getRecommendedStorage())
        .build();
  }
}
