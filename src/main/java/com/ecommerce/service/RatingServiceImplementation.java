package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.model.Ratings;
import com.ecommerce.model.User;
import com.ecommerce.reposatory.RatingReposatory;
import com.ecommerce.request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService {

	private RatingReposatory ratingReposatory;
	private ProductService productService;

	public RatingServiceImplementation(RatingReposatory ratingReposatory, ProductService productService) {
		super();
		this.ratingReposatory = ratingReposatory;
		this.productService = productService;
	}

	@Override
	public Ratings createRating(User user, RatingRequest req) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		Ratings ratings = new Ratings();
		ratings.setProduct(product);
		ratings.setUser(user);
		ratings.setRating(req.getRating());

		return ratingReposatory.save(ratings);
	}

	@Override
	public List<Ratings> getProductRating(Long productId) {
		Optional<Ratings> byId = ratingReposatory.findById(productId);

		return (List<Ratings>) byId.get();

	}

}
