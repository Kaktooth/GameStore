package com.store.gamestore.service.requirements;

import com.store.gamestore.persistence.entity.SystemRequirements;
import com.store.gamestore.service.CommonService;
import java.util.UUID;

public interface SystemRequirementsService extends CommonService<SystemRequirements, UUID> {

  SystemRequirements findByGameProfileId(UUID gameProfileId);
}
