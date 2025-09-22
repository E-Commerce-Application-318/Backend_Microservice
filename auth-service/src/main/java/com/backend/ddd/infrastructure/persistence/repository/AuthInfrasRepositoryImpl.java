package com.backend.ddd.infrastructure.persistence.repository;

import com.backend.ddd.domain.model.entity.User;
import com.backend.ddd.domain.repository.AuthDomainRepository;
import com.backend.ddd.infrastructure.persistence.mapper.AuthJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthInfrasRepositoryImpl implements AuthDomainRepository {
    
    @Autowired
    private AuthJPAMapper authJPAMapper;
    
    @Override
    public Optional<User> getUserByUsername(String username) {
        return authJPAMapper.findByUsername(username);
    }
    
    @Override
    public Optional<User> getUserByEmail(String email) {
        return authJPAMapper.findByEmail(email);
    }
    
    @Override
    @Transactional
    public User save(User user) {
        return authJPAMapper.saveAndFlush(user);
    }
    
    @Override
    public Boolean existsByUsername(String username) {
        return authJPAMapper.findByUsername(username).isPresent();
    }
    
    @Override
    public Boolean existsByEmail(String email) {
        return authJPAMapper.findByEmail(email).isPresent();
    }

    @Override
    public Optional<User> getUserDetail(UUID userID) {
        return authJPAMapper.findById(userID);
    }
}
