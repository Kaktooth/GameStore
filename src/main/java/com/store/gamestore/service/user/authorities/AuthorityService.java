package com.store.gamestore.service.user.authorities;

import com.store.gamestore.persistence.entity.Authority;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService extends AbstractService<Authority, UUID> {

  public AuthorityService(
      CommonRepository<Authority, UUID> repository) {
    super(repository);
  }
}
