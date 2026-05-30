package com.appservice.effectiverestapi.repository;

import com.appservice.effectiverestapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EffectiveRestApiRepository extends JpaRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByIdempotencyKey(String idempotencyKey);
}
