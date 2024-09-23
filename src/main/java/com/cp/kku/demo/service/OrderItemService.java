package com.cp.kku.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.kku.demo.model.OrderItem;
import com.cp.kku.demo.repository.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItems() {
        return (List<OrderItem>)orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(int id) {
        OrderItem orderItem = orderItemRepository.findById(id)
	      	      .orElseThrow(() -> new IllegalArgumentException("Invalid OrderItem Id:" + id));
		return orderItem;
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    public OrderItem updateOrderItem(int id, OrderItem orderItem) {
		OrderItem existingOrderItem = orderItemRepository.findById(id).get();
		existingOrderItem.setProduct(orderItem.getProduct());
        existingOrderItem.setOrder(orderItem.getOrder());
        existingOrderItem.setPrice(orderItem.getPrice());
        existingOrderItem.setQuantity(orderItem.getQuantity());
	    return orderItemRepository.save(existingOrderItem);
	}

    public void deleteOrderItem(int id) {
        orderItemRepository.deleteById(id);
    }
}
