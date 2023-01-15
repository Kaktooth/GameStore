package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.Domain;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface CommonRepository<T extends Domain, I extends Serializable> extends
    JpaRepository<T, I>,
    PagingAndSortingRepository<T, I>,
    JpaSpecificationExecutor<T> {

}
