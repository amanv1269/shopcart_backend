package com.ecommerce.service;

import java.util.List;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.Ratings;
import com.ecommerce.model.User;
import com.ecommerce.request.RatingRequest;

public interface RatingService {

	public Ratings createRating(User user, RatingRequest req) throws ProductException;

	public List<Ratings> getProductRating(Long productId);

}
