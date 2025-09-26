package com.backend.ddd.domain.service.impl;

import com.backend.ddd.domain.model.entity.Payment;
import com.backend.ddd.domain.model.entity.User;
import com.backend.ddd.domain.repository.AuthDomainRepository;
import com.backend.ddd.domain.service.AuthDomainService;
import com.backend.ddd.infrastructure.persistence.mapper.UserJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthDomainServiceImpl implements AuthDomainService {

    @Autowired
    AuthDomainRepository authDomainRepository;
    @Autowired
    private UserJPAMapper authJPAMapper;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return authDomainRepository.getUserByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String username) {
        return authDomainRepository.getUserByEmail(username);
    }

    @Override
    public User registerUser(User user) {
        return authDomainRepository.save(user);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return authDomainRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return authDomainRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> getUserDetail(UUID userId) {
        return authDomainRepository.getUserDetail(userId);
    }

    @Override
    public Payment getPaymentByUserId(UUID userId) {
        return authDomainRepository.getPaymentByUserId(userId);
    }
}
