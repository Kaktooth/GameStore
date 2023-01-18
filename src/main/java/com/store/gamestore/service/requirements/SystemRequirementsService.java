package com.store.gamestore.service.requirements;

import com.store.gamestore.persistence.entity.SystemRequirements;
import java.util.UUID;

public interface SystemRequirementsService {

  SystemRequirements findByGameProfileId(UUID gameProfileId);
}
