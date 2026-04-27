package com.microscopic.h2dbservice.service;

import com.microscopic.h2dbservice.entity.User;
import com.microscopic.h2dbservice.exception.ResourceNotFoundException;
import com.microscopic.h2dbservice.repository.H2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class H2Service {

    private static final Logger log = LoggerFactory.getLogger(H2Service.class);

    @Autowired
    private H2Repository h2Repository;

    public User createUser(User user) {
        log.info("Saving user data to db");
        return h2Repository.save(user);
    }

    public User updateUser(User user, int age) {
        log.info("Updating user data for userId: {}", user.getId());
        User existingUser = getUser(user.getId());
        if(existingUser != null) {
            existingUser.setAge(String.valueOf(age));
        }
        return existingUser;
    }

    public User getUser(Long id) {
        log.info("Getting user data for userId: {}", id);
        return h2Repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
