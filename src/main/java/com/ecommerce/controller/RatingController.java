package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Ratings;
import com.ecommerce.model.User;
import com.ecommerce.request.RatingRequest;
import com.ecommerce.service.RatingService;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("api/ratings")
public class RatingController {

	@Autowired
	private UserService userService;
	@Autowired
	private RatingService ratingService;

	@GetMapping("/create")
	public ResponseEntity<Ratings> createRatings(@RequestHeader("Authorization") String token,
			@RequestBody RatingRequest req) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(token);

		Ratings rating = ratingService.createRating(user, req);
		return new ResponseEntity<Ratings>(HttpStatus.OK);
	}

	@GetMapping("/{productId}/ratings")
	public ResponseEntity<List<Ratings>> productRatings(@RequestBody Long productId)
			throws UserException, ProductException {

		List<Ratings> productRating = ratingService.getProductRating(productId);
		return new ResponseEntity<List<Ratings>>(productRating, HttpStatus.OK);
	}
}
