package com.store.gamestore.common.mapper;

import com.store.gamestore.model.dto.SystemRequirementsDTO;
import com.store.gamestore.persistence.entity.SystemRequirements;

public interface SystemRequirementsMapper {

  SystemRequirementsDTO sourceToDestination(SystemRequirements systemRequirements);
}
