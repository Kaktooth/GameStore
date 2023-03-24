package com.store.gamestore.service.enumeration;

import com.store.gamestore.persistence.repository.enumeration.CommonEnumerationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AbstractEnumerationService<T, I extends Number> implements
    CommonEnumerationService<T, I> {

  protected final CommonEnumerationRepository<T, I> repository;

  @Override
  public T get(I id) {
    return repository.getById(id);
  }

  @Override
  public List<T> getAll() {
    return repository.findAll();
  }

  @Override
  public List<T> getAll(Iterable<I> ids) {
    return repository.findAllById(ids);
  }
}
