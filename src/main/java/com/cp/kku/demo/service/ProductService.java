package com.cp.kku.demo.service;

import com.cp.kku.demo.model.Product;
import com.cp.kku.demo.model.Image;
import com.cp.kku.demo.repository.ProductRepository;
import com.cp.kku.demo.repository.ImageRepository;
import com.cp.kku.demo.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    // Save product along with an image
    public Product saveProduct(Product product, MultipartFile imageFile) throws IOException {
        // Save image first
        Image image = Image.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                .build();
        
        Image savedImage = imageRepository.save(image);
        
        // Set the saved image to the product
        product.setImage(savedImage);
        
        // Save product
        return productRepository.save(product);
    }

    // Update a product
    public Product updateProduct(int productId, Product productDetails, MultipartFile imageFile) throws IOException {
        Optional<Product> productOptional = productRepository.findById(productId);
        
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            
            // Update product details
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());
            product.setCategory(productDetails.getCategory());
            product.setManufacturer(productDetails.getManufacturer());
            product.setWarrantyPeriod(productDetails.getWarrantyPeriod());
            product.setRating(productDetails.getRating());
            
            // If image is updated, save the new image
            if (imageFile != null) {
                Image newImage = Image.builder()
                        .name(imageFile.getOriginalFilename())
                        .type(imageFile.getContentType())
                        .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                        .build();
                imageRepository.delete(product.getImage());
                Image savedImage = imageRepository.save(newImage);
                product.setImage(savedImage);
            }
            
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    // Get all products
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    // Get product by ID
    public Product getProductById(int productId) {
        return productRepository.findById(productId).get();
    }

    // Delete a product
    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }
}
