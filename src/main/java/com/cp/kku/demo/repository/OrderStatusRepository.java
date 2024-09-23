package com.cp.kku.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.OrderStatus;

@Repository
public interface OrderStatusRepository extends CrudRepository<OrderStatus, Integer>{
    OrderStatus findByOrderOrderId(int orderId);
}
