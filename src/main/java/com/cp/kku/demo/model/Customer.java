package com.cp.kku.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(columnDefinition = "TEXT")
    private String billingAddress;

    // @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    // private Set<Order> orders;

    // @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    // private Set<Review> reviews;
    public Customer(){
        super();
    }
    public Customer(int customerId, String firstName, String lastName, String email, String username,
            String shippingAddress, String billingAddress,User user) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        
    }
    // , Set<Order> orders, Set<Review> reviews
    // this.orders = orders;
    // this.reviews = reviews;
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    // public Set<Order> getOrders() {
    //     return orders;
    // }

    // public void setOrders(Set<Order> orders) {
    //     this.orders = orders;
    // }

    // public Set<Review> getReviews() {
    //     return reviews;
    // }

    // public void setReviews(Set<Review> reviews) {
    //     this.reviews = reviews;
    // }

    
}

