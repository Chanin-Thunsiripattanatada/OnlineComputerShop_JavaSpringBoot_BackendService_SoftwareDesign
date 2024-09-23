package com.cp.kku.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cp.kku.demo.dtos.JwtRequest;
import com.cp.kku.demo.dtos.RegistrationUserDto;
import com.cp.kku.demo.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        // return authService.createNewUser(registrationUserDto);
        authService.createNewUser(registrationUserDto);
        JwtRequest authRequest = new JwtRequest();
        authRequest.setUsername(registrationUserDto.getUsername());
        authRequest.setPassword(registrationUserDto.getPassword());
        return authService.createAuthToken(authRequest);

    }
}
