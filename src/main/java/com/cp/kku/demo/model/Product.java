package com.cp.kku.demo.model;

import javax.persistence.*;


@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false,referencedColumnName = "categoryId")
    private Category category;

    @Column
    private String manufacturer;

    @Column
    private int warrantyPeriod;

    @OneToOne
    @JoinColumn(name = "image_id",nullable = false,referencedColumnName = "id")
    private Image image;

    @Column
    private double rating;

    public Product(){
        super();
    }
    public Product(int productId, String name, String description, double price, int stockQuantity, Category category,
            String manufacturer, int warrantyPeriod, Image imageid, double rating) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.manufacturer = manufacturer;
        this.warrantyPeriod = warrantyPeriod;
        this.image = imageid;
        this.rating = rating;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    
}

