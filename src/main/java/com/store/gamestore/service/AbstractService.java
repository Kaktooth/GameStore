package com.store.gamestore.service;

import com.store.gamestore.persistence.entity.Domain;
import com.store.gamestore.persistence.repository.CommonRepository;

import java.io.Serializable;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AbstractService<T extends Domain, I extends Serializable> implements CommonService<T, I> {

    protected final CommonRepository<T, I> repository;

    public AbstractService(CommonRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T object) {
        return repository.save(object);
    }

    @Override
    @Transactional(readOnly = true)
    public T get(I id) {
        return repository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll(Iterable<I> ids) {
        return repository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public void update(T object) {
        repository.save(object);
    }

    @Override
    public void delete(I id) {
        repository.deleteById(id);
    }
}
