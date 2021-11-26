package com.muhammad.warehouse.repository;

import com.muhammad.warehouse.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByEmail(String email);
}
