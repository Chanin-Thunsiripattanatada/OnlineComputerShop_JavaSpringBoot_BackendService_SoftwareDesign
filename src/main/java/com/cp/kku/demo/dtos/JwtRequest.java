package com.cp.kku.demo.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
    
}
