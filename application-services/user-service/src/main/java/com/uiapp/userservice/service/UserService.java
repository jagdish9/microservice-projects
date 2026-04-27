package com.uiapp.userservice.service;

import com.uiapp.userservice.controller.UserController;
import com.uiapp.userservice.dto.UserDto;
import com.uiapp.userservice.entity.User;
import com.uiapp.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .age(userDto.getAge())
                .build();

        log.info("Creating user: {}", user.getId());
        return userRepository.save(user);
    }
}
