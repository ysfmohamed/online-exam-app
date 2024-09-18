package com.joe.authservice.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.joe.authservice.dtos.LoginDto;
import com.joe.authservice.dtos.SignupDto;
import com.joe.authservice.services.KeycloakService;
import com.joe.authservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final KeycloakService keycloakService;
    private final UserService userService;

    public AuthController(KeycloakService keycloakService, UserService userService) {
        this.keycloakService = keycloakService;
        this.userService = userService;
    }

    @PostMapping("/${endpoint.auth-controller.signup}")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody @Valid SignupDto userDto) {
        String userId = this.keycloakService.addUser(userDto);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @PostMapping(path = "/${endpoint.auth-controller.login}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JsonNode> login(LoginDto user) {
        return keycloakService.login(user);
    }

    @PostMapping("/${endpoint.auth-controller.logout}")
    public void logout(@PathVariable String userId) {
        keycloakService.logout(userId);
    }
}
