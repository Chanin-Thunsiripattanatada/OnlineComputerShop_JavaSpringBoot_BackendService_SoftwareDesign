package com.cp.kku.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.Image;

import java.util.Optional;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    Optional<Image> findByName(String name);
}