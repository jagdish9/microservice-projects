package com.microscopic.exceptionservice.controller;

import com.microscopic.exceptionservice.entity.ExceptionEmployee;
import com.microscopic.exceptionservice.service.ExceptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    private final ExceptionService exceptionService;

    public ExceptionController(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }

    @GetMapping("/{id}")
    public ExceptionEmployee getEmployee(@PathVariable Long id) {
        return exceptionService.getEmployee(id);
    }
}
