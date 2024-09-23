package com.cp.kku.demo.controller;

import com.cp.kku.demo.model.Product;
import com.cp.kku.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int Id) {
        Product product = productService.getProductById(Id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @SuppressWarnings("null")
    @PostMapping("/admin/products")
    public ResponseEntity<Product> addNewProduct(
            @RequestParam("product") String productJson,
            @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            // Convert the JSON string to a Product object
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productJson, Product.class);
            System.out.println(product.getCategory().getCategoryName());
            Product createdProduct = productService.saveProduct(product, imageFile);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("null")
    @PutMapping("/admin/products/{id}") // update
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") int productId,
            @RequestPart("product") String productJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product productDetails = objectMapper.readValue(productJson, Product.class);
            Product updatedProduct = productService.updateProduct(productId, productDetails, imageFile);
            if (updatedProduct != null) {
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

}
