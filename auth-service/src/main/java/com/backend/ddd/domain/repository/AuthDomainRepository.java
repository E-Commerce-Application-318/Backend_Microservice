package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.User;

import java.util.Optional;

public interface AuthDomainRepository {
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    User save(User user);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
