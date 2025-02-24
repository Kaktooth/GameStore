package com.store.gamestore.service.requirements;

import com.store.gamestore.persistence.entity.SystemRequirements;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.requirements.SystemRequirementsRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class SystemRequirementsServiceImpl extends AbstractService<SystemRequirements, UUID>
    implements SystemRequirementsService {

  public SystemRequirementsServiceImpl(
      CommonRepository<SystemRequirements, UUID> repository) {
    super(repository);
  }

  @Override
  public SystemRequirements findByGameProfileId(UUID gameProfileId) {
    return ((SystemRequirementsRepository) repository).findByGameProfileId(gameProfileId);
  }
}
