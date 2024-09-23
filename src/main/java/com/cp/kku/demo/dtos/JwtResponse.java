package com.cp.kku.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class JwtResponse {
    private String token;
}
