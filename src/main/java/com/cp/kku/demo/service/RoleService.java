package com.cp.kku.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.cp.kku.demo.model.Role;
import com.cp.kku.demo.repository.RoleRepository;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}