package com.cp.kku.demo.controller;

import java.security.Principal;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cp.kku.demo.model.Customer;
import com.cp.kku.demo.model.Order;
import com.cp.kku.demo.model.OrderItem;
import com.cp.kku.demo.model.User;
import com.cp.kku.demo.service.CustomerService;
import com.cp.kku.demo.service.OrderService;
import com.cp.kku.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/admin/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/user/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") int id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user/orders/customer/{id}")
    public ResponseEntity<?> getOrderCustomer(@PathVariable("id") int id, Principal principal) {
        Customer customer = customerService.getCustomerById(id);
        User user = userService.findByUsername(principal.getName()).get();
        if (user == customer.getUser()) {
            List<Order> orders = orderService.getOrderCustomer(customer);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("userID != CustomerID", HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/user/orders/{id}/orderItems")
    public ResponseEntity<List<OrderItem>> getOrderItemsInOrder(@PathVariable("id") int id) {
        List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(id);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }


    @SuppressWarnings("null")
    @PutMapping("/user/orders/{id}") // update
    public ResponseEntity<?> updateOrder(
            @PathVariable("id") int orderId,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            Order order = orderService.getOrderById(orderId);
            order.setStatus("under-review");
            order.setShippingStatus("not-shipped");
            Order updatedOrder = orderService.updateOrder(orderId, order, imageFile);
            if (updatedOrder != null) {
                return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/orders/create")
    public ResponseEntity<?> addNewOrder(@RequestBody Order order, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        Customer customer = customerService.getCustomerByUserId(user.getId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("pending-confirmation");
        order.setShippingStatus("not-shipped");
        if (order.getCustomer().getCustomerId() == customer.getCustomerId()) {
            Order savedOrder = orderService.saveOrder(order);
            return ResponseEntity.ok(savedOrder);
        } else {
            return new ResponseEntity<>("userID != CustomerID", HttpStatus.FORBIDDEN);
        }

    }

    @PutMapping("/admin/orders/{id}") // update
    ResponseEntity<Order> updateOrder(@RequestBody Order newOrder,
            @PathVariable("id") int id) {
        Order orderUpdate = newOrder;
        Order updateOrder = orderService.updateOrder(id, orderUpdate);
        return ResponseEntity.ok(updateOrder);
    }

    @DeleteMapping("/admin/orders/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") int id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }

}
