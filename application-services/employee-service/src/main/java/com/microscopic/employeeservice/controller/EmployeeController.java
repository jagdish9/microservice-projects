package com.microscopic.employeeservice.controller;

import com.microscopic.employeeservice.entity.Employee;
import com.microscopic.employeeservice.entity.EmployeeList;
import com.microscopic.employeeservice.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    public final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create-employee")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee,
                                            @RequestHeader("idempotency-key") String idempotencyKey) {
        log.info("Creating employee: {}", employee.getName());
        return new ResponseEntity<>(employeeService.createEmployee(employee, idempotencyKey), HttpStatus.CREATED);
    }

    @PostMapping("/create-employees")
    public ResponseEntity<?> createEmployees(@RequestBody EmployeeList employees) {
        log.info("Creating total employees: {}", employees.getEmpList().size());
        return new ResponseEntity<>(employeeService.createEmployees(employees), HttpStatus.CREATED);
    }

}
