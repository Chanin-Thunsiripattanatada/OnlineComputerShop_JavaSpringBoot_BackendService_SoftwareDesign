package com.cp.kku.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.Customer;
import com.cp.kku.demo.model.Order;
import java.util.List;



@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByCustomer(Customer customer);
}

