package com.cp.kku.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.kku.demo.model.Category;
import com.cp.kku.demo.model.Product;
import com.cp.kku.demo.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return (List<Category>)categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        Category category = categoryRepository.findById(id)
	      	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		return category;
    }
    // get Product in category
    public List<Product> getProductInCategory(int id){
        Category category = getCategoryById(id);
        List<Product> products = category.getProducts();
        return products;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    public Category updateCategory(int id, Category category) {
		Category existingCategory = categoryRepository.findById(id).get();
		existingCategory.setCategoryName(category.getCategoryName());
	    return categoryRepository.save(existingCategory);
	}

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}
