package com.cp.kku.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.Image;
import com.cp.kku.demo.model.PrivateImage;

import java.util.Optional;

@Repository
public interface PrivateImageRepository extends CrudRepository<PrivateImage, Long> {
    Optional<Image> findByName(String name);
}