package com.cp.kku.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.kku.demo.model.Review;
import com.cp.kku.demo.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return (List<Review>)reviewRepository.findAll();
    }

    public Review getReviewById(int id) {
        Review review = reviewRepository.findById(id)
	      	      .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + id));
		return review;
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }
}

