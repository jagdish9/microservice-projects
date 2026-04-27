package com.application.premiumservice.repository;

import com.application.premiumservice.entity.Premium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PremiumRepository extends JpaRepository<Premium, String> {

    Optional<Premium> findByIdempotencyKey(String idempotencyKey);
}
