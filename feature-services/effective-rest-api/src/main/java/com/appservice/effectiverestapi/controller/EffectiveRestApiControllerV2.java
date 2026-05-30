package com.appservice.effectiverestapi.controller;

import com.appservice.effectiverestapi.dto.EmployeeDto;
import com.appservice.effectiverestapi.entity.Employee;
import com.appservice.effectiverestapi.service.EffectiveRestApiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/employees")
@Tag(name = "Employee API v2", description = "Operations related to Employee")
public class EffectiveRestApiControllerV2 {

    private final static Logger log = LoggerFactory.getLogger(EffectiveRestApiControllerV2.class);

    @Autowired
    private EffectiveRestApiService effectiveRestApiService;

    @PutMapping("/secure/{id}")
    public Employee updateEmp(@RequestBody EmployeeDto employeeDto,
                              @PathVariable Long id) {
        return effectiveRestApiService.updateEmpV2(employeeDto, id);
    }
}
