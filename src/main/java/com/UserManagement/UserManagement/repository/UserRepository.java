package com.UserManagement.UserManagement.repository;

import com.UserManagement.UserManagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByPassword(String password);
}


