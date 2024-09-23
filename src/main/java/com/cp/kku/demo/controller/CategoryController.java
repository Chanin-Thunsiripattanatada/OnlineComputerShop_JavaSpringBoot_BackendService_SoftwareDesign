package com.cp.kku.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp.kku.demo.model.Category;
import com.cp.kku.demo.model.Product;
import com.cp.kku.demo.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired 
    private CategoryService categoryService;
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categorys = categoryService.getAllCategories();
        return new ResponseEntity<>(categorys, HttpStatus.OK);
    }
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    @GetMapping("/categories/{id}/products")
    public ResponseEntity<List<Product>> getProductInCategory(@PathVariable("id") int id) {
        List<Product> products = categoryService.getProductInCategory(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/admin/categories")
    public Category addNewCategory(@RequestBody Category category) {
        System.out.println(category);
        Category savedCategory = categoryService.saveCategory(category);
        return savedCategory;
    }
    @PutMapping("/admin/categories/{id}") // update
    ResponseEntity<Category> updateCategory(@RequestBody Category newCategory,
            @PathVariable("id") int id) {
        Category categoryUpdate = newCategory;
        Category updateProduct = categoryService.updateCategory(id,categoryUpdate);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

}


