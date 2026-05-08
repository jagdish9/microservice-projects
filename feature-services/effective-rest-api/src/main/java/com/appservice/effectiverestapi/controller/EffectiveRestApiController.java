package com.appservice.effectiverestapi.controller;

import com.appservice.effectiverestapi.dto.EmployeeDto;
import com.appservice.effectiverestapi.entity.Employee;
import com.appservice.effectiverestapi.record.EmployeeResult;
import com.appservice.effectiverestapi.service.EffectiveRestApiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee API", description = "Operations related to Employee")
public class EffectiveRestApiController {

    private final static Logger log = LoggerFactory.getLogger(EffectiveRestApiController.class);

    private final EffectiveRestApiService effectiveRestApiService;

    public EffectiveRestApiController(EffectiveRestApiService effectiveRestApiService) {
        this.effectiveRestApiService = effectiveRestApiService;
    }

    @PostMapping("/secure/employee")
    public ResponseEntity<?> createEmp(@Valid @RequestBody EmployeeDto employeeDto,
                                       @RequestHeader("idempotency-key") String idempotencyKey) {
        return new ResponseEntity<>(effectiveRestApiService.createEmp(employeeDto, idempotencyKey), HttpStatus.CREATED);
    }

    @GetMapping("/public/employee-list")
    public ResponseEntity<?> getEmp(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Page<Employee> result = effectiveRestApiService.getEmp(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<?> getEmpById(@PathVariable Long id) {
        return new ResponseEntity<>(effectiveRestApiService.getEmpById(id), HttpStatus.OK);
    }

    @PutMapping("/secure/{id}")
    public ResponseEntity<Employee> updateEmp(@RequestBody EmployeeDto employeeDto,
                                              @RequestHeader("idempotency-key") String idempotencyKey, @PathVariable Long id) {
        EmployeeResult employeeResult = effectiveRestApiService.updateEmp(employeeDto, idempotencyKey, id);
        if (employeeResult.isCreated()) {
            return new ResponseEntity<>(employeeResult.emp(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(employeeResult.emp(), HttpStatus.OK);
        }
    }

    @PatchMapping("/secure/{id}")
    public ResponseEntity<?> updateEmpById(@RequestParam int age, @PathVariable Long id) {
        return new ResponseEntity<>(effectiveRestApiService.updateById(age, id), HttpStatus.OK);
    }

    @DeleteMapping("/secure/{id}")
    public ResponseEntity<?> deleteEmpById(@PathVariable Long id) {
        return new ResponseEntity<>(effectiveRestApiService.deleteById(id), HttpStatus.OK);
    }

    @GetMapping("/public/employee-by-page")
    public Page<Employee> getEmployee(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return effectiveRestApiService.getEmpList(name, email, department, page, size, sortBy, direction);
    }

    @GetMapping("/public/debug")
    public Map<String, Object> debug(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();
    }
}
