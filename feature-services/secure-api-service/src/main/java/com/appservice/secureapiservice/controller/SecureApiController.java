package com.appservice.secureapiservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class SecureApiController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Public API - No token required";
    }

    @GetMapping("/user/profile")
    public String getProfile() {
        return "User Profile - Authenticated";
    }

    @PostMapping("/user/create")
    public String createUser() {
        return "User Created";
    }

    @PutMapping("/admin/update")
    public String updateData() {
        return "Data updated by Admin";
    }

    @DeleteMapping("/admin/delete")
    public String deleteData() {
        return "Data deleted by Admin";
    }

    @GetMapping("/debug")
    public Map<String, Object> debug(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();
    }
}
