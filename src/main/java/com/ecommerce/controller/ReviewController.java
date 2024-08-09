package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Review;
import com.ecommerce.model.User;
import com.ecommerce.request.ReviewRequest;
import com.ecommerce.service.ReviewService;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("api/ratings")
public class ReviewController {

	@Autowired
	private UserService userService;
	@Autowired
	private ReviewService reviewService;

	@PostMapping("/create")
	public ResponseEntity<Review> createReviews(@RequestHeader("Authorization") String token,
			@RequestBody ReviewRequest req) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(token);

		Review review = reviewService.createReview(req, user);
		return new ResponseEntity<Review>(HttpStatus.OK);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> productreview(@RequestBody Long productId)
			throws UserException, ProductException {

		List<Review> reviews = reviewService.getAllProductReviews(productId);
		return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
	}
}
