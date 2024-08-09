package com.ecommerce.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Ratings;

public interface RatingReposatory extends JpaRepository<Ratings, Long> {

}
