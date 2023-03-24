package com.store.gamestore.persistence.repository.requirements;

import com.store.gamestore.persistence.entity.SystemRequirements;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.UUID;

public interface SystemRequirementsRepository extends CommonRepository<SystemRequirements, UUID> {

  SystemRequirements findByGameProfileId(UUID gameProfileId);
}