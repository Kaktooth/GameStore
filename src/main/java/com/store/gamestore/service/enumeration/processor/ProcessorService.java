package com.store.gamestore.service.enumeration.processor;

import com.store.gamestore.model.entity.Processor;
import com.store.gamestore.repository.enumeration.CommonEnumerationRepository;
import com.store.gamestore.service.enumeration.AbstractEnumerationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessorService extends AbstractEnumerationService<Processor, Integer> {
    public ProcessorService(CommonEnumerationRepository<Processor, Integer> repository) {
        super(repository);
    }
}
