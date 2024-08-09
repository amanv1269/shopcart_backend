package com.ecommerce.reposatory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Review;

public interface ReviewReposatory extends JpaRepository<Review, Long> {

//	@Query("SELECT r FROM REVIEW r WHERE r.product.id=:productId")
//	public List<Review> getAllProductReviews(@Param("product") Long productId);
	@Query("SELECT r FROM Review r WHERE r.product.id = :productId")
	List<Review> getAllProductReviews(@Param("productId") Long productId);
}
