package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.model.User;
import com.ecommerce.reposatory.ReviewReposatory;
import com.ecommerce.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {

	private ReviewReposatory reviewReposatory;

	private ProductService productService;

	public ReviewServiceImplementation(ReviewReposatory reviewReposatory, ProductService productService) {
		super();
		this.reviewReposatory = reviewReposatory;
		this.productService = productService;
	}

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());

		Review review = new Review();
		review.setProduct(product);
		review.setUser(user);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		return reviewReposatory.save(review);
	}

//	@Override
//	public List<Review> getAllReviews(Long productId) {
//		Optional<Review> opt = reviewReposatory.findById(productId);
//		return (List<Review>) opt.get();
//	}

	@Override
	public List<Review> getAllProductReviews(Long productId) {

		return reviewReposatory.getAllProductReviews(productId);
	}

}
