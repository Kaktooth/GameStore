package com.store.gamestore.persistence.repository.enumeration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface CommonEnumerationRepository<T, I extends Number> extends
    JpaRepository<T, I>,
    PagingAndSortingRepository<T, I>,
    JpaSpecificationExecutor<T> {

}
