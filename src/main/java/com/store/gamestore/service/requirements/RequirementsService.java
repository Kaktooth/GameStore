package com.store.gamestore.service.requirements;

import com.store.gamestore.model.ComputerComponent;

import java.util.Set;

public interface RequirementsService {
    Set<ComputerComponent> getProcessorNames();

    Integer getProcessorId(String processor);

    Set<ComputerComponent> getGraphicsCardNames();

    Integer getGraphicCardId(String graphicCard);

    Set<ComputerComponent> getOSNames();

    Integer getOSId(String OS);
}
