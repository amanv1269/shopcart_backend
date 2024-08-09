package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.OrderException;
import com.ecommerce.model.Order;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> order = orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(order, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String entity) throws OrderException {
		Order order = orderService.confirmedorder(orderId);

		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String entity) throws OrderException {
		Order order = orderService.shippedOrder(orderId);

		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String entity) throws OrderException {
		Order order = orderService.deliverdOrder(orderId);

		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String entity) throws OrderException {
		Order order = orderService.canceledOrder(orderId);

		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<Order> deleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String entity) throws OrderException {
		orderService.deleteOrder(orderId);
		ApiResponse res = new ApiResponse();

		res.setMessage("Order Delete Succesfully");
		res.setStatus(true);
		return new ResponseEntity<Order>(HttpStatus.ACCEPTED);
	}
}
