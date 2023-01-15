package com.store.gamestore.persistence.repository.enumeration;

import com.store.gamestore.persistence.entity.Domain;
import java.io.Serializable;
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
