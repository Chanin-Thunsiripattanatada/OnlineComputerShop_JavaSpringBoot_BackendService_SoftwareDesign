package com.cp.kku.demo.controller;


import com.cp.kku.demo.model.Order;

import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketOrderController {
    
    @SendTo("/topic/notifications")
    public String sendOrderNotification(Order order, String message) {
        String notificationMessage = message + " for Order ID: " + order.getOrderId();
        return notificationMessage;
    }
}