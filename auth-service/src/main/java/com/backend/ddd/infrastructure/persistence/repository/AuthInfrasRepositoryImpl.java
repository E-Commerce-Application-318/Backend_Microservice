package com.backend.ddd.infrastructure.persistence.repository;

import com.backend.ddd.domain.model.entity.Payment;
import com.backend.ddd.domain.model.entity.User;
import com.backend.ddd.domain.repository.AuthDomainRepository;
import com.backend.ddd.infrastructure.persistence.mapper.PaymentJPAMapper;
import com.backend.ddd.infrastructure.persistence.mapper.UserJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthInfrasRepositoryImpl implements AuthDomainRepository {
    
    @Autowired
    private UserJPAMapper userJPAMapper;

    @Autowired
    private PaymentJPAMapper paymentJPAMapper;
    
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userJPAMapper.findByUsername(username);
    }
    
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userJPAMapper.findByEmail(email);
    }
    
    @Override
    @Transactional
    public User save(User user) {
        return userJPAMapper.saveAndFlush(user);
    }
    
    @Override
    public Boolean existsByUsername(String username) {
        return userJPAMapper.findByUsername(username).isPresent();
    }
    
    @Override
    public Boolean existsByEmail(String email) {
        return userJPAMapper.findByEmail(email).isPresent();
    }

    @Override
    public Optional<User> getUserDetail(UUID userID) {
        return userJPAMapper.findById(userID);
    }

    @Override
    public Payment getPaymentByUserId(UUID userId) {
        return paymentJPAMapper.findByUserId(userId);
    }
}
