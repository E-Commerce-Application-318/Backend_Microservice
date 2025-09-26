package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.Payment;
import com.backend.ddd.domain.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface AuthDomainRepository {
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    User save(User user);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> getUserDetail(UUID userID);

    // Payment database
    Payment getPaymentByUserId(UUID userId);
}
