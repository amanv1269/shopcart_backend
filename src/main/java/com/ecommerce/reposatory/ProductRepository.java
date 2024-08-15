package com.ecommerce.reposatory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Product;

public interface ProductReposatory extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " + "WHERE (p.category.name = :category OR :category = '') "
            + "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) "
            + "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " + "ORDER BY "
            + "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, "
            + "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
    List<Product> filterProducts(@Param("category") String category, @Param("sort") String sort,
                                 @Param("maxPrice") Integer maxPrice, @Param("minPrice") Integer minPrice,
                                 @Param("minDiscount") Integer minDiscount);



    @Query("SELECT p FROM Product p WHERE p.category.name = :category")
    List<Product> filterProductsByCategory(@Param("category") String category);
    }
