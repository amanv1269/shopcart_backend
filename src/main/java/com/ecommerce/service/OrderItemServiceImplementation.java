package com.ecommerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.model.OrderItem;
import com.ecommerce.reposatory.OrderItemReposatory;

@Service
public class OrderItemServiceImplementation implements OrderItemService {

	private OrderItemReposatory orderItemReposatory;

	public OrderItemServiceImplementation(OrderItemReposatory orderItemReposatory) {
		super();
		this.orderItemReposatory = orderItemReposatory;
	}

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		// TODO Auto-generated method stub
		return orderItemReposatory.save(orderItem);
	}

//
	@Override
	public OrderItem findOrderItemById(Long orderItemId) {
		Optional<OrderItem> optional = orderItemReposatory.findById(orderItemId);
		return optional.get();
	}

//	  @Override
//	    public OrderItem findOrderItemById(Long orderItemId) {
//	        return orderItemReposatory.findById(orderItemId)
//	            .orElseThrow(() -> new RuntimeException("OrderItem not found for id: " + orderItemId));
//	    }

}
