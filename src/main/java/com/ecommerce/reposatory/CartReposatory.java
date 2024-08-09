package com.ecommerce.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Cart;

public interface CartReposatory extends JpaRepository<Cart, Long> {

//	@Query("SELECT c FROM CART c where c.user.id=:userId")
	@Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
	Cart findByUserId(@Param("userId") Long userId);
//	public Cart findByUserId(@Param("userID") Long userId);
}
