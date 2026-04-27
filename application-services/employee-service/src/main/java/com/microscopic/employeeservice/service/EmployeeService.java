package com.microscopic.employeeservice.service;

import com.microscopic.employeeservice.entity.Employee;
import com.microscopic.employeeservice.entity.EmployeeList;
import com.microscopic.employeeservice.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    public final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee, String idempotencyKey) {
        return employeeRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {
                    Employee newEmployee = Employee.builder()
                            .name(employee.getName())
                            .age(employee.getAge())
                            .department(employee.getDepartment())
                            .salary(employee.getSalary())
                            .idempotencyKey(idempotencyKey)
                            .build();
                    return employeeRepository.save(newEmployee);
                });
    }

    public List<Employee> createEmployees(EmployeeList employees) {
        List<Employee> empList = employees.getEmpList()
                .stream()
                .peek(emp -> emp.setIdempotencyKey(UUID.randomUUID().toString()))
                .collect(Collectors.toList());
        return employeeRepository.saveAll(empList);
    }
}
