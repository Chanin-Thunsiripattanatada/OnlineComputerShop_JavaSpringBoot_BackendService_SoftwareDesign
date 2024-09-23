package com.cp.kku.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
