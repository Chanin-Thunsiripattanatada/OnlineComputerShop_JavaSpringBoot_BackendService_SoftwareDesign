package com.cp.kku.demo.model;

import javax.persistence.*;


@Entity
@Table(name = "OrderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false,referencedColumnName = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false,referencedColumnName = "productId")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;
    public OrderItem(){
        super();
    }
    public OrderItem(int orderItemId, Order order, Product product, int quantity, double price) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
    
}

