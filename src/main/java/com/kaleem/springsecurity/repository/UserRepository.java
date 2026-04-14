package com.kaleem.springsecurity.repository;

import com.kaleem.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> getUserByUsername(String username);
}
