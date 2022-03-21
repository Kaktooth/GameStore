package com.store.gamestore.service.requirements;

import com.store.gamestore.model.ComputerComponent;

import java.util.List;

public interface RequirementsService {
    List<ComputerComponent> getProcessorNames();

    Integer getProcessorId(String processor);

    List<ComputerComponent> getGraphicsCardNames();

    Integer getGraphicCardId(String graphicCard);

    List<ComputerComponent> getOSNames();

    Integer getOSId(String OS);
}
