package com.backend.ddd.domain.service;

import com.backend.ddd.domain.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface AuthDomainService {
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String username);
    User registerUser(User user);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> getUserDetail(UUID userId);
}
