package com.ecommerce.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.OrderItem;

public interface OrderItemReposatory extends JpaRepository<OrderItem, Long> {

	OrderItem save(OrderItem orderItem);

}
