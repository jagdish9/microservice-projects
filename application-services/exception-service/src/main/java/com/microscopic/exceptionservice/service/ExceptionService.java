package com.microscopic.exceptionservice.service;

import com.microscopic.exceptionservice.entity.ExceptionEmployee;
import com.microscopic.exceptionservice.exception.ResourceNotFoundException;
import com.microscopic.exceptionservice.repository.ExceptionRepository;
import org.springframework.stereotype.Service;

@Service
public class ExceptionService {

    private final ExceptionRepository exceptionRepository;

    public ExceptionService(ExceptionRepository exceptionRepository) {
        this.exceptionRepository = exceptionRepository;
    }

    public ExceptionEmployee getEmployee(Long id) {
        return exceptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: "+id));
    }
}
