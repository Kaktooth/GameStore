package com.store.gamestore.persistence.repository.user.authorities;

import com.store.gamestore.persistence.entity.Authority;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorityRepository extends CommonRepository<Authority, UUID> {

}