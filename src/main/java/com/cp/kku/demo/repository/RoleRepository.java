package com.cp.kku.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.Role;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer>{
    Optional<Role> findByName(String name);
}
