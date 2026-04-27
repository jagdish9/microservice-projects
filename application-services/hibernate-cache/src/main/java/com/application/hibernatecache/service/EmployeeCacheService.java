package com.application.hibernatecache.service;

import com.application.hibernatecache.entity.EmployeeCache;
import com.application.hibernatecache.repository.EmployeeCacheRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeCacheService {

    private final EmployeeCacheRepository repository;

    public EmployeeCacheService(EmployeeCacheRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public EmployeeCache createEmployee(EmployeeCache employeeCache) {
        return repository.save(employeeCache);
    }

    @Transactional(readOnly = true)
    public EmployeeCache getEmployee(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
