package com.ecommerce.service;

import com.ecommerce.model.OrderItem;

public interface OrderItemService {

	public OrderItem createOrderItem(OrderItem orderItem);

	public OrderItem findOrderItemById(Long orderItemId);
}
