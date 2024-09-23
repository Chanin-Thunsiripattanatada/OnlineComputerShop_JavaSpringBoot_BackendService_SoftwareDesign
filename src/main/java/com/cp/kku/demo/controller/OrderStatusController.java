package com.cp.kku.demo.controller;

import com.cp.kku.demo.model.OrderStatus;
import com.cp.kku.demo.model.PrivateImage;
import com.cp.kku.demo.service.OrderService;
import com.cp.kku.demo.service.OrderStatusService;
import com.cp.kku.demo.service.PrivateImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderStatusController {

    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PrivateImageService privateImageService;

    // Get all OrderStatus records
    @GetMapping
    public ResponseEntity<List<OrderStatus>> getAllOrderStatuses() {
        List<OrderStatus> orderStatuses = orderStatusService.getAllOrderStatuses();
        return new ResponseEntity<>(orderStatuses, HttpStatus.OK);
    }

    // Get OrderStatus by ID
    @GetMapping("/user/order-status/{id}")
    public ResponseEntity<OrderStatus> getOrderStatusById(@PathVariable("id") int statusId) {
        Optional<OrderStatus> orderStatus = orderStatusService.getOrderStatusById(statusId);
        if (orderStatus.isPresent()) {
            return new ResponseEntity<>(orderStatus.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/order-status/order/{orderId}")
    public ResponseEntity<OrderStatus> getOrderStatusByOrderId(@PathVariable("orderId") int orderId) {
        OrderStatus orderStatuse = orderStatusService.getOrderStatusByOrderId(orderId);
        if (orderStatuse != null) {
            return new ResponseEntity<>(orderStatuse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create new OrderStatus with an image upload
    @SuppressWarnings("null")
    @PostMapping("/user/order-status/create")
    public ResponseEntity<OrderStatus> createOrderStatus(
            @RequestParam("orderId") int orderId,
            @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            // Save the image first
            PrivateImage savedImage = privateImageService.uploadImage(imageFile);

            // Create OrderStatus object and set the image
            OrderStatus orderStatus = new OrderStatus();
            
            orderStatus.setOrder(orderService.getOrderById(orderId));
            orderStatus.setPrivateimage(savedImage);
            orderStatus.setStatus("กำลังตรวจสอบ");
            orderStatus.setVerificationDate(LocalDateTime.now());
            orderStatus.setAdminNote("กำลังตรวจสอบ");

            // Save the OrderStatus record
            OrderStatus createdOrderStatus = orderStatusService.createOrUpdateOrderStatus(orderStatus);
            // order.set
            // orderService.saveOrder(order);
            return new ResponseEntity<>(createdOrderStatus, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update existing OrderStatus (with or without image update)
    @SuppressWarnings("null")
    @PutMapping("/user/order-status/update/{id}")
    public ResponseEntity<OrderStatus> updateOrderStatus(
            @PathVariable("id") int statusId,
            @RequestParam("orderId") int orderId,
            @RequestParam("status") String status,
            @RequestParam("verificationDate") String verificationDate,
            @RequestParam("adminNote") String adminNote,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        Optional<OrderStatus> existingOrderStatus = orderStatusService.getOrderStatusById(statusId);
        if (existingOrderStatus.isPresent()) {
            OrderStatus orderStatus = existingOrderStatus.get();

            // Update the OrderStatus fields
            orderStatus.setStatus(status);
            orderStatus.setStatus(status);
            orderStatus.setVerificationDate(LocalDateTime.parse(verificationDate));
            orderStatus.setAdminNote(adminNote);

            // Check if a new image was uploaded
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    // Upload and replace the old image with the new one
                    PrivateImage updatedImage = privateImageService.uploadImage(imageFile);
                    orderStatus.setPrivateimage(updatedImage);
                } catch (IOException e) {
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            // Save the updated OrderStatus
            OrderStatus updatedOrderStatus = orderStatusService.createOrUpdateOrderStatus(orderStatus);
            return new ResponseEntity<>(updatedOrderStatus, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete OrderStatus by ID
    @DeleteMapping("/user/order-status/delete/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable("id") int statusId) {
        orderStatusService.deleteOrderStatus(statusId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
