package com.microscopic.h2dbservice.controller;

import com.microscopic.h2dbservice.entity.User;
import com.microscopic.h2dbservice.service.H2Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class H2Controller {

    private final H2Service h2Service;

    public H2Controller(H2Service h2Service) {
        this.h2Service = h2Service;
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return new ResponseEntity<>(h2Service.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(h2Service.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/update-age")
    public ResponseEntity<?> updateUser(@RequestBody User user,
                                        @RequestParam int age) {
        return new ResponseEntity<>(h2Service.updateUser(user, age), HttpStatus.OK);
    }
}
