package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.OrderItem;
import com.ecommerce.service.OrderItemService;

@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;

	@GetMapping("/{orderItemId}")
	ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long orderItemId,
			@RequestHeader("Authorization") String token) {
		OrderItem orderItem = orderItemService.findOrderItemById(orderItemId);
		return new ResponseEntity<OrderItem>(orderItem, HttpStatus.OK);
	}

}
