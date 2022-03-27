package com.store.gamestore.service.requirements;

import com.store.gamestore.model.Requirements;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequirementsService extends AbstractService<Requirements, Integer> {

    public RequirementsService(CommonRepository<Requirements, Integer> repository) {
        super(repository);
    }
}
