package com.cp.kku.demo.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.cp.kku.demo.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByUserId(int userId);
}
