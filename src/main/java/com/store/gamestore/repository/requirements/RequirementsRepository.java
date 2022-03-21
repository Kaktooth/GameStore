package com.store.gamestore.repository.requirements;

import com.store.gamestore.model.ComputerComponent;

import java.util.List;

public interface RequirementsRepository {

    List<ComputerComponent> getProcessorNames();

    Integer getProcessorId(String processor);

    List<ComputerComponent> getGraphicCardNames();

    Integer getGraphicCardId(String graphicCard);

    List<ComputerComponent> getOSNames();

    Integer getOSId(String OS);
}
