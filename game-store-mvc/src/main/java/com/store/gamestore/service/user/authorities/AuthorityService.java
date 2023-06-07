package com.store.gamestore.service.user.authorities;

import com.store.gamestore.persistence.entity.Authority;
import com.store.gamestore.service.CommonService;
import java.util.UUID;

public interface AuthorityService extends CommonService<Authority, UUID> {

  Authority findAuthorityByUserId(UUID id);
}
