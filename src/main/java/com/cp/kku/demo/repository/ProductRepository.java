package com.cp.kku.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.Category;
import com.cp.kku.demo.model.Product;
import java.util.List;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
}
