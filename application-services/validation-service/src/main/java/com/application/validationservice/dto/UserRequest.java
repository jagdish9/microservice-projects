package com.application.validationservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid Email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Min(value = 18, message = "Age must be >= 18")
    @Max(value = 60, message = "Age must be <= 60")
    private int age;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile No. must be 10 digits")
    private String mobile;
}
