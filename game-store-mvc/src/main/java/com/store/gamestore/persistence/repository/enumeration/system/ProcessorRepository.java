package com.store.gamestore.persistence.repository.enumeration.system;

import com.store.gamestore.persistence.entity.Processor;
import com.store.gamestore.persistence.repository.enumeration.CommonEnumerationRepository;

public interface ProcessorRepository extends CommonEnumerationRepository<Processor, Integer> {

  Processor findProcessorByName(String name);
}