package com.cp.kku.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import java.time.LocalDateTime;


@Entity
@Table(name = "OrderStatus")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int statusId;
    
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "orderId")
    private Order order;

    @OneToOne
    @JoinColumn(name = "private_image_id",referencedColumnName = "id")
    private PrivateImage privateimage;

    @Column(nullable = false)
    private String status;

    @Column
    private LocalDateTime verificationDate;

    @Column(columnDefinition = "TEXT")
    private String adminNote; 

    public OrderStatus() {
    }

    public OrderStatus(int statusId, Order order, PrivateImage privateimage, String status,
            LocalDateTime verificationDate, String adminNote) {
        this.statusId = statusId;
        this.order = order;
        this.privateimage = privateimage;
        this.status = status;
        this.verificationDate = verificationDate;
        this.adminNote = adminNote;
    }



    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PrivateImage getPrivateimage() {
        return privateimage;
    }

    public void setPrivateimage(PrivateImage privateimage) {
        this.privateimage = privateimage;
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

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }
    
    
}