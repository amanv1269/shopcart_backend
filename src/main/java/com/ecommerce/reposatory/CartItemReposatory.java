package com.ecommerce.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

public interface CartItemReposatory extends JpaRepository<CartItem, Long> {

	@Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product") Product product,
			@Param("size") String size, @Param("userId") Long userId);
}
