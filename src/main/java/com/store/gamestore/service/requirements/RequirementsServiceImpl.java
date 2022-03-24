package com.store.gamestore.service.requirements;

import com.store.gamestore.model.ComputerComponent;
import com.store.gamestore.model.Requirements;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.repository.requirements.RequirementsRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class RequirementsServiceImpl extends AbstractService<Requirements, Integer> implements RequirementsService {

    public RequirementsServiceImpl(CommonRepository<Requirements, Integer> repository) {
        super(repository);
    }

    @Override
    public Set<ComputerComponent> getProcessorNames() {
        return ((RequirementsRepository) repository).getProcessorNames();
    }

    @Override
    public Set<ComputerComponent> getGraphicsCardNames() {
        return ((RequirementsRepository) repository).getGraphicCardNames();
    }

    @Override
    public Set<ComputerComponent> getOSNames() {
        return ((RequirementsRepository) repository).getOSNames();
    }

    @Override
    public Integer getProcessorId(String processor) {
        return ((RequirementsRepository) repository).getProcessorId(processor);
    }

    @Override
    public Integer getGraphicCardId(String graphicCard) {
        return ((RequirementsRepository) repository).getGraphicCardId(graphicCard);
    }

    @Override
    public Integer getOSId(String os) {
        return ((RequirementsRepository) repository).getOSId(os);
    }
}
