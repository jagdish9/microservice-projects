package com.application.hibernatecache.controller;

import com.application.hibernatecache.entity.EmployeeCache;
import com.application.hibernatecache.service.EmployeeCacheService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeCacheController {

    private final EmployeeCacheService service;

    public EmployeeCacheController(EmployeeCacheService service) {
        this.service = service;
    }

    @PostMapping("/create-employee")
    public EmployeeCache createEmployee(@RequestBody EmployeeCache employeeCache) {
        return service.createEmployee(employeeCache);
    }

    @GetMapping("/{id}")
    public EmployeeCache getEmployee(@PathVariable Long id) {
        return service.getEmployee(id);
    }
}
