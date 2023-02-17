package com.store.gamestore.service.enumeration.os;

import com.store.gamestore.persistence.entity.OperatingSystem;
import com.store.gamestore.persistence.repository.enumeration.CommonEnumerationRepository;
import com.store.gamestore.service.enumeration.AbstractEnumerationService;
import org.springframework.stereotype.Service;

@Service
public class OperatingSystemService extends AbstractEnumerationService<OperatingSystem, Integer> {

  public OperatingSystemService(CommonEnumerationRepository<OperatingSystem, Integer> repository) {
    super(repository);
  }
}
