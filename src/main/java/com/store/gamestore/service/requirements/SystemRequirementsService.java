package com.store.gamestore.service.requirements;

import com.store.gamestore.persistence.entity.SystemRequirements;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class SystemRequirementsService extends AbstractService<SystemRequirements, UUID> {

  public SystemRequirementsService(
      CommonRepository<SystemRequirements, UUID> repository) {
    super(repository);
  }
}
