package com.application.validationservice.controller;

import com.application.validationservice.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/create-user")
    public String createUser(@Valid @RequestBody UserRequest request) {
        return "User is valid and created successfully";
    }
}

/*
@Valid triggers validation automatically.
 */

/*
Key Interview Points
Validation happens before controller logic
Uses Hibernate Validator (JSR-380 / Jakarta Validation)
@Valid vs @Validated
@Valid → basic
@Validated → supports groups
Always use GlobalExceptionHandler in real projects
Avoid putting validation in service layer → keep in DTO
 */