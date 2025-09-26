package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentJPAMapper extends JpaRepository<Payment, UUID> {
    @Query("select p from Payment p where p.userId = :userId")
    Payment findByUserId(@Param("userId") UUID userId);
}
