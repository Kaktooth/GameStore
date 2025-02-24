package com.store.gamestore.persistence.repository.enumeration.system;

import com.store.gamestore.persistence.entity.GraphicsCard;
import com.store.gamestore.persistence.repository.enumeration.CommonEnumerationRepository;

public interface GraphicsCardRepository extends CommonEnumerationRepository<GraphicsCard, Integer> {
GraphicsCard findGraphicCardByName(String name);
}