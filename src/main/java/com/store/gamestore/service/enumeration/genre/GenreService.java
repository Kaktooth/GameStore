package com.store.gamestore.service.enumeration.genre;

import com.store.gamestore.model.Genre;
import com.store.gamestore.repository.enumeration.CommonEnumerationRepository;
import com.store.gamestore.service.enumeration.AbstractEnumerationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GenreService extends AbstractEnumerationService<Genre, Integer> {
    public GenreService(CommonEnumerationRepository<Genre, Integer> repository) {
        super(repository);
    }
}
