package com.cp.kku.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.cp.kku.demo.dtos.AuthResponse;
import com.cp.kku.demo.dtos.JwtRequest;
import com.cp.kku.demo.dtos.RegistrationUserDto;
import com.cp.kku.demo.dtos.UserDto;
import com.cp.kku.demo.exceptions.AppError;
import com.cp.kku.demo.model.Customer;
import com.cp.kku.demo.model.User;
import com.cp.kku.demo.util.JwtTokenUtils;



@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final CustomerService customerService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "UnAuthrized"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        User user = userService.findByUsername(authRequest.getUsername()).get();
        Customer customer = null ;
        try {
            customer = customerService.getCustomerByUserId(user.getId());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return ResponseEntity.ok(new AuthResponse(token,user,customer));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto((long)user.getId(), user.getUsername(), user.getEmail()));
    }
}

