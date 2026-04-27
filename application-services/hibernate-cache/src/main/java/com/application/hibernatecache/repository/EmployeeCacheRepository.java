package com.application.hibernatecache.repository;

import com.application.hibernatecache.entity.EmployeeCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCacheRepository extends JpaRepository<EmployeeCache, Long> {
}
