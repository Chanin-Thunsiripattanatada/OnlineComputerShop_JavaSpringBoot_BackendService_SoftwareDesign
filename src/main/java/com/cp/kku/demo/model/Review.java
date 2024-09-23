package com.cp.kku.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false,referencedColumnName = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false,referencedColumnName = "customerId")
    private Customer customer;

    @Column(nullable = false)
    private double rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false, updatable = false)
    private LocalDateTime reviewDate;
    public Review(){
        super();
    }
    public Review(int reviewId, Product product, Customer customer, double rating, String comment,
            LocalDateTime reviewDate) {
        this.reviewId = reviewId;
        this.product = product;
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    
}

