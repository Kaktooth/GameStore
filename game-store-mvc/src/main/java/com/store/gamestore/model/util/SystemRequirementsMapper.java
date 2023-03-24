package com.store.gamestore.model.util;

import com.store.gamestore.model.dto.SystemRequirementsDTO;
import com.store.gamestore.persistence.entity.SystemRequirements;

public interface SystemRequirementsMapper {

  SystemRequirementsDTO sourceToDestination(SystemRequirements systemRequirements);
}
