package com.cp.kku.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cp.kku.demo.model.User;
import com.cp.kku.demo.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private UserService userService;
    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured data";
    }
    @GetMapping("/secured")
    public String securedData() {
        return "secured data";
    }
    @GetMapping("/admin")
    public String adminData() {
        return "admin data";
    }
    @GetMapping("/info")
    public User userrData(Principal principal) {
        return userService.findByUsername(principal.getName()).get();
    }
   
}