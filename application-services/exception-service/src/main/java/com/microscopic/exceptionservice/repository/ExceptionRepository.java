package com.microscopic.exceptionservice.repository;

import com.microscopic.exceptionservice.entity.ExceptionEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionRepository extends JpaRepository<ExceptionEmployee, Long> {
}
