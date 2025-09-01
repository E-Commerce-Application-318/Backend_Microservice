package com.backend.ddd.domain.service.impl;

import com.backend.ddd.domain.model.entity.User;
import com.backend.ddd.domain.repository.AuthDomainRepository;
import com.backend.ddd.domain.service.AuthDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthDomainServiceImpl implements AuthDomainService {

    @Autowired
    AuthDomainRepository authDomainRepository;

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
}
