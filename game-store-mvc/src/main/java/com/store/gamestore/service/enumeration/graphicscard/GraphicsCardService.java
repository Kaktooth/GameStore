package com.store.gamestore.service.enumeration.graphicscard;

import com.store.gamestore.persistence.entity.GraphicsCard;
import com.store.gamestore.persistence.repository.enumeration.CommonEnumerationRepository;
import com.store.gamestore.service.enumeration.AbstractEnumerationService;
import org.springframework.stereotype.Service;

@Service
public class GraphicsCardService extends AbstractEnumerationService<GraphicsCard, Integer> {

  public GraphicsCardService(CommonEnumerationRepository<GraphicsCard, Integer> repository) {
    super(repository);
  }
}
