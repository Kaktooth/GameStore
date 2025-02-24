package com.store.gamestore.service.user.authorities;

import com.store.gamestore.persistence.entity.Authority;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.user.authorities.AuthorityRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl extends AbstractService<Authority, UUID>
    implements AuthorityService {

  public AuthorityServiceImpl(
      CommonRepository<Authority, UUID> repository) {
    super(repository);
  }

  @Override
  public Authority findAuthorityByUserId(UUID id) {
    return ((AuthorityRepository) repository).findAuthorityByUserId(id);
  }
}
