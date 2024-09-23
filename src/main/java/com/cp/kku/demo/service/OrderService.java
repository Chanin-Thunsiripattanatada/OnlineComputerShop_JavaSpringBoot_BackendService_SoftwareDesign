package com.cp.kku.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.cp.kku.demo.controller.WebSocketOrderController;
import com.cp.kku.demo.model.Customer;
import com.cp.kku.demo.model.PrivateImage;
import com.cp.kku.demo.model.Order;
import com.cp.kku.demo.model.OrderItem;
import com.cp.kku.demo.repository.OrderRepository;
import com.cp.kku.demo.repository.PrivateImageRepository;
import com.cp.kku.demo.util.ImageUtils;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private WebSocketOrderController webSocketOrderController;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PrivateImageRepository privateImageRepository;

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Order Id:" + id));
        return order;
    }

    public List<Order> getOrderCustomer(Customer customer) {
        List<Order> orders = orderRepository.findByCustomer(customer);
        return orders;
    }

    // get orderItems in order
    public List<OrderItem> getOrderItemsByOrderId(int id) {
        Order order = getOrderById(id);
        List<OrderItem> orderItems = order.getOrderItems();
        return orderItems;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order saveOrder(Order order, MultipartFile imageFile) throws IOException {
        // Save image first
        PrivateImage image = PrivateImage.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                .build();

        PrivateImage savedImage = privateImageRepository.save(image);

        // Set the saved image to the product
        order.setPrivateImage(savedImage);
        Order savedOrder = orderRepository.save(order);

        webSocketOrderController.sendOrderNotification(savedOrder, "Order Created");
        // Save product
        return savedOrder;
    }

    public Order updateOrder(int id, Order order, MultipartFile imageFile) throws IOException {
        Order existingOrder = getOrderById(id); // This will throw exception if not found
        // Update fields
        existingOrder.setCustomer(order.getCustomer());
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setShippingStatus(order.getShippingStatus());
        existingOrder.setTotalAmount(order.getTotalAmount());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setVerificationDate(order.getVerificationDate());
        existingOrder.setAdminNote(order.getAdminNote());

        // If image is provided, update it
        if (imageFile != null) {
            PrivateImage newImage = PrivateImage.builder()
                    .name(imageFile.getOriginalFilename())
                    .type(imageFile.getContentType())
                    .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                    .build();

            // Delete the old image if it exists
            if (existingOrder.getPrivateImage() != null) {
                privateImageRepository.delete(existingOrder.getPrivateImage());
            }
            // Save the new image and set it to the order
            PrivateImage savedImage = privateImageRepository.save(newImage);
            existingOrder.setPrivateImage(savedImage);

            webSocketOrderController.sendOrderNotification(existingOrder, "Order Image to Verify Updated");
        }

        // Save the updated order
        return orderRepository.save(existingOrder);
    }

    public Order updateOrder(int id, Order order) {
        // Retrieve the existing order
        Order existingOrder = orderRepository.findById(id).get();

        // Update fields
        existingOrder.setCustomer(order.getCustomer());
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setTotalAmount(order.getTotalAmount());
        existingOrder.setShippingStatus(order.getShippingStatus());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setVerificationDate(order.getVerificationDate());
        existingOrder.setPrivateImage(order.getPrivateImage());
        existingOrder.setAdminNote(order.getAdminNote());
        boolean statusChanged = !existingOrder.getShippingStatus().equals(order.getShippingStatus());
        Order updatedOrder = orderRepository.save(existingOrder);
        if (statusChanged) {
            webSocketOrderController.sendOrderNotification(updatedOrder,
                    "Order Status Updated to " + updatedOrder.getShippingStatus());
        }

        // Save the updated order
        return updatedOrder;
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
