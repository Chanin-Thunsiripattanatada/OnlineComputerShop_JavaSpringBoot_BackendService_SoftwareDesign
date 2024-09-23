package com.cp.kku.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp.kku.demo.model.OrderItem;
import com.cp.kku.demo.service.OrderItemService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class OrderItemController {

    @Autowired 
    private OrderItemService orderItemService;
    @GetMapping("/user/orderItems")
    public ResponseEntity<List<OrderItem>> getOrderItems() {
        List<OrderItem> orderItem = orderItemService.getAllOrderItems();
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @GetMapping("/user/orderItems/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable("id") int Id) {
        OrderItem orderItem = orderItemService.getOrderItemById(Id);
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @PostMapping("/user/orderItems")
    public OrderItem addNewOrderItem(@RequestBody OrderItem orderItem) {
        System.out.println(orderItem);

        OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem);
        return savedOrderItem;
    }

    @PutMapping("/user/orderItems/{id}") // update
    ResponseEntity<OrderItem> updateOrderItem(@RequestBody OrderItem newOrderItem,
            @PathVariable("id") int id) {
        OrderItem orderItemUpdate = newOrderItem;
        OrderItem updateOrderItem = orderItemService.updateOrderItem(id,orderItemUpdate);
        return ResponseEntity.ok(updateOrderItem);
    }

    @DeleteMapping("/user/orderItems/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable("id") int id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok("OrderItem deleted successfully");
    }

}

