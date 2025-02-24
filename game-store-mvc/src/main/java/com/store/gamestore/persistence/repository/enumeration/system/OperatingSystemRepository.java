package com.store.gamestore.persistence.repository.enumeration.system;

import com.store.gamestore.persistence.entity.OperatingSystem;
import com.store.gamestore.persistence.repository.enumeration.CommonEnumerationRepository;

public interface OperatingSystemRepository extends
    CommonEnumerationRepository<OperatingSystem, Integer> {
  OperatingSystem findOperatingSystemByName(String name);
}