package com.joe.authservice.services;

import com.joe.authservice.dtos.SignupDto;
import com.joe.authservice.entities.User;
import com.joe.authservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(String userId, SignupDto userDto) {
        User newUser = new User(
                userId,
                userDto.getUsername(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole()
        );

        return userRepository.save(newUser);
    }

    public String getUserId(String username) {
        return userRepository.findByUsername(username).getUserId();
    }
}
