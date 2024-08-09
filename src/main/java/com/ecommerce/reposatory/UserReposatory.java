package com.ecommerce.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.User;

public interface UserReposatory extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	@Query("SELECT u FROM User u JOIN u.address a WHERE a.id = :addressId")
	public User findUserByAddressId(@Param("addressId") Long addressId);
}
