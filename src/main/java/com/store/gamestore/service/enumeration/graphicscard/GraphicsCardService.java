package com.store.gamestore.service.enumeration.graphicscard;

import com.store.gamestore.model.GraphicsCard;
import com.store.gamestore.repository.enumeration.CommonEnumerationRepository;
import com.store.gamestore.service.enumeration.AbstractEnumerationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GraphicsCardService extends AbstractEnumerationService<GraphicsCard, Integer> {
    public GraphicsCardService(CommonEnumerationRepository<GraphicsCard, Integer> repository) {
        super(repository);
    }
}
