package com.cp.kku.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.Order;
import com.cp.kku.demo.model.OrderItem;
import java.util.List;


@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);
}
