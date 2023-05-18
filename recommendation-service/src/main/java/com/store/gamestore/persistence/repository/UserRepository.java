package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.User;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

}
