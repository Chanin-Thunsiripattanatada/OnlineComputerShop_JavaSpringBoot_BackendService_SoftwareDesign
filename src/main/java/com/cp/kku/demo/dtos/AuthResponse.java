package com.cp.kku.demo.dtos;


import com.cp.kku.demo.model.Customer;
import com.cp.kku.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse{
    private String token;
    private User user;
    private Customer customer;
    
}