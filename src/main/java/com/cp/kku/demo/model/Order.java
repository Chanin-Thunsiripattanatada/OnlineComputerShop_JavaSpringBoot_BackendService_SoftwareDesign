package com.cp.kku.demo.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "customerId")
    private Customer customer;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private double totalAmount;

    @Column
    private String shippingStatus;

    @Column(nullable = false)
    private String status;

    @Column
    private LocalDateTime verificationDate;
    
    @OneToOne
    @JoinColumn(name = "private_image_id", referencedColumnName = "id")
    private PrivateImage privateImage;  


    @Column(columnDefinition = "TEXT")
    private String adminNote;

    @JsonIgnoreProperties("order")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order() {
        super();
    }

    public Order(int orderId, Customer customer, LocalDateTime orderDate, double totalAmount, String shippingStatus,
            String status, LocalDateTime verificationDate, PrivateImage privateImage, String adminNote,
            List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.shippingStatus = shippingStatus;
        this.status = status;
        this.verificationDate = verificationDate;
        this.privateImage = privateImage;
        this.adminNote = adminNote;
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(LocalDateTime verificationDate) {
        this.verificationDate = verificationDate;
    }

    public PrivateImage getPrivateImage() {
        return privateImage;
    }

    public void setPrivateImage(PrivateImage privateImage) {
        this.privateImage = privateImage;
    }


    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


    
}

