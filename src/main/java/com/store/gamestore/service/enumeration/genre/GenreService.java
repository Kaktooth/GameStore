package com.store.gamestore.service.enumeration.genre;

import com.store.gamestore.persistence.entity.Genre;
import com.store.gamestore.persistence.repository.enumeration.CommonEnumerationRepository;
import com.store.gamestore.service.enumeration.AbstractEnumerationService;
import org.springframework.stereotype.Service;

@Service
public class GenreService extends AbstractEnumerationService<Genre, Integer> {

  public GenreService(CommonEnumerationRepository<Genre, Integer> repository) {
    super(repository);
  }
}
