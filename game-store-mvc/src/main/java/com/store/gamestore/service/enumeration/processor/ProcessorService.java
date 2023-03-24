package com.store.gamestore.service.enumeration.processor;

import com.store.gamestore.persistence.entity.Processor;
import com.store.gamestore.persistence.repository.enumeration.CommonEnumerationRepository;
import com.store.gamestore.service.enumeration.AbstractEnumerationService;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService extends AbstractEnumerationService<Processor, Integer> {

  public ProcessorService(CommonEnumerationRepository<Processor, Integer> repository) {
    super(repository);
  }
}
