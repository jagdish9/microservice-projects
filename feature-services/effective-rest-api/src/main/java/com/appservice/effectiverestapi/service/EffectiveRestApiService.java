package com.appservice.effectiverestapi.service;

import com.appservice.effectiverestapi.dto.EmployeeDto;
import com.appservice.effectiverestapi.entity.Employee;
import com.appservice.effectiverestapi.exception.ResourceNotFoundException;
import com.appservice.effectiverestapi.record.EmployeeResult;
import com.appservice.effectiverestapi.repository.EffectiveRestApiRepository;
import com.appservice.effectiverestapi.specification.EmployeeSpecification;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EffectiveRestApiService {

    private final static Logger log = LoggerFactory.getLogger(EffectiveRestApiService.class);

    private final EffectiveRestApiRepository effectiveRestApiRepository;

    public EffectiveRestApiService(EffectiveRestApiRepository effectiveRestApiRepository) {
        this.effectiveRestApiRepository = effectiveRestApiRepository;
    }

    @CacheEvict(value = "employees", key = "#result.id")
    public Employee createEmp(@Valid EmployeeDto employeeDto, String idempotencyKey) {
        log.info("Creating new employee");
        return effectiveRestApiRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {
                    Employee employee = Employee.builder()
                            .name(employeeDto.getName())
                            .email(employeeDto.getEmail())
                            .age(employeeDto.getAge())
                            .department(employeeDto.getDepartment())
                            .salary(employeeDto.getSalary())
                            .mobile(employeeDto.getMobile())
                            .idempotencyKey(idempotencyKey)
                            .createdAt(LocalDateTime.now())
                            .build();
                    return effectiveRestApiRepository.save(employee);
                });
    }

    public Page<Employee> getEmp(int page, int size) {;
        log.info("Fetching employee by page: {} with size: {}", page, size);
        return effectiveRestApiRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @Cacheable(value = "employees", key = "#id")
    public Employee getEmpById(Long id) {
        log.info("Fetching employee data from DB for id: {}", id);
        return effectiveRestApiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: "+id));
    }

    @CacheEvict(value = "employees", key = "#result.emp.id")
    public EmployeeResult updateEmp(EmployeeDto employeeDto, String idempotencyKey, Long id) {
        log.info("Checking to update employee data for id: {}", id);
        Employee existingEmployee = getEmpById(id);
        if(existingEmployee != null && idempotencyKey.equals(existingEmployee.getIdempotencyKey())) {
            log.info("Updating data");
            existingEmployee.setSalary(employeeDto.getSalary());
            existingEmployee.setUpdatedAt(LocalDateTime.now());
            return new EmployeeResult(effectiveRestApiRepository.save(existingEmployee), false);
        } else {
            log.info("Creating data");
            Employee employee = Employee.builder()
                    .name(employeeDto.getName())
                    .email(employeeDto.getEmail())
                    .age(employeeDto.getAge())
                    .department(employeeDto.getDepartment())
                    .salary(employeeDto.getSalary())
                    .mobile(employeeDto.getMobile())
                    .idempotencyKey(idempotencyKey)
                    .createdAt(LocalDateTime.now())
                    .build();
            return new EmployeeResult(effectiveRestApiRepository.save(employee), true);
        }
    }

    @CacheEvict(value = "employees", key = "#result.id")
    public Employee updateById(int age, Long id) {
        log.info("Updating age for id: {}", id);
        Employee existingEmployee = getEmpById(id);

        existingEmployee.setAge(age);

        return effectiveRestApiRepository.save(existingEmployee);
    }

    @CacheEvict(value = "employees", key = "#result.id")
    public String deleteById(Long id) {
        log.info("Deleting employee data for id: {}", id);
        effectiveRestApiRepository.deleteById(id);
        return "Employee deleted successfully";
    }

    public Page<Employee> getEmpList(String name, String email, String department,
                                     int page, int size, String sortBy, String direction) {
        log.info("Applying searching, sorting");
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return effectiveRestApiRepository.findAll(
                EmployeeSpecification.filterEmployee(name, email, department),
                pageable
        );
    }

    public Employee updateEmpV2(EmployeeDto employeeDto, Long id) {
        log.info("Updating employee data for id: {}", id);
        Employee existingEmployee = getEmpById(id);
        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setEmail(employeeDto.getEmail());
        existingEmployee.setAge(employeeDto.getAge());
        existingEmployee.setDepartment(employeeDto.getDepartment());
        existingEmployee.setSalary(employeeDto.getSalary());
        existingEmployee.setMobile(employeeDto.getMobile());
        //existingEmployee.setIdempotencyKey(employeeDto.getIdempotencyKey());
        existingEmployee.setUpdatedAt(LocalDateTime.now());
        return effectiveRestApiRepository.save(existingEmployee);
    }
}
