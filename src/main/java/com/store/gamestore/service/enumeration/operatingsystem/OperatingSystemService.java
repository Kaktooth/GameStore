package com.store.gamestore.service.enumeration.operatingsystem;

import com.store.gamestore.model.entity.OperatingSystem;
import com.store.gamestore.repository.enumeration.CommonEnumerationRepository;
import com.store.gamestore.service.enumeration.AbstractEnumerationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperatingSystemService extends AbstractEnumerationService<OperatingSystem, Integer> {
    public OperatingSystemService(CommonEnumerationRepository<OperatingSystem, Integer> repository) {
        super(repository);
    }
}
