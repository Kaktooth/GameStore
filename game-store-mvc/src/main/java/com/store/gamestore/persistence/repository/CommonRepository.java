package com.store.gamestore.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface CommonRepository<T, I> extends
    JpaRepository<T, I>,
    PagingAndSortingRepository<T, I>,
    JpaSpecificationExecutor<T> {

}
