package com.appservice.effectiverestapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeDto {

    private Long id;

    @NotBlank(message = "Name can not be empty")
    private String name;

    @Email(message = "Invalid Email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Min(value = 18, message = "Age must be >= 18")
    private int age;

    @NotBlank(message = "Department can not be empty")
    private String department;

    @NotNull(message = "Salary can not be null")
    private double salary;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile No. must be 10 digits")
    private String mobile;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /*@NotBlank(message = "idempotency-key is must")
    @Column(name = "idempotency-key")
    private String idempotencyKey;*/
}
