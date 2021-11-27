package com.muhammad.warehouse.repository;

import com.muhammad.warehouse.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByEmail(String email);
    boolean existsByEmail(String email);
    User findUserById(long id);
}
