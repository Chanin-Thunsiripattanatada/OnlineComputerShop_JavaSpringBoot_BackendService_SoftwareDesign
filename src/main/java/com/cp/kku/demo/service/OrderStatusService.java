package com.cp.kku.demo.service;

import com.cp.kku.demo.model.OrderStatus;
import com.cp.kku.demo.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusService {

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public List<OrderStatus> getAllOrderStatuses() {
        return (List<OrderStatus>) orderStatusRepository.findAll();
    }

    public Optional<OrderStatus> getOrderStatusById(int statusId) {
        return orderStatusRepository.findById(statusId);
    }
    
    public OrderStatus getOrderStatusByOrderId(int orderId) {
        return orderStatusRepository.findByOrderOrderId(orderId);
    }

    public OrderStatus createOrUpdateOrderStatus(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    public void deleteOrderStatus(int statusId) {
        orderStatusRepository.deleteById(statusId);
    }
}
