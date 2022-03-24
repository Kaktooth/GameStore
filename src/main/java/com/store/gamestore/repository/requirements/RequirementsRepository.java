package com.store.gamestore.repository.requirements;

import com.store.gamestore.model.ComputerComponent;

import java.util.Set;

public interface RequirementsRepository {

    Set<ComputerComponent> getProcessorNames();

    Integer getProcessorId(String processor);

    Set<ComputerComponent> getGraphicCardNames();

    Integer getGraphicCardId(String graphicCard);

    Set<ComputerComponent> getOSNames();

    Integer getOSId(String OS);
}
