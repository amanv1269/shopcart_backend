//package com.ecommerce.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ecommerce.Exception.OrderException;
//import com.ecommerce.Exception.UserException;
//import com.ecommerce.model.Address;
//import com.ecommerce.model.Order;
//import com.ecommerce.model.User;
//import com.ecommerce.service.OrderService;
//import com.ecommerce.service.UserService;
//
//@RestController
//@RequestMapping("/api/orders")
//public class OrderController {
//	@Autowired
//	private OrderService orderService;
//	@Autowired
//	private UserService userService;
//
//	@PostMapping("/create")
//	public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String token,
//			@RequestBody Address shippingAddress) throws UserException {
//		User user = userService.findUserProfileByJwt(token);
//		Order order = orderService.createOrder(user, shippingAddress);
//		System.err.println("order" + order);
//		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
//	}
//
//	@GetMapping("/user")
//	public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("Authorization") String token) throws UserException {
//		User user = userService.findUserProfileByJwt(token);
//
//		List<Order> allorder = orderService.usersOrderHishtory(user.getId());
//		return new ResponseEntity<List<Order>>(HttpStatus.OK);
//	}
//
////	@GetMapping("/{orderId}")
////	public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String token, @PathVariable Long orderId)
////			throws UserException, OrderException {
////		User user = userService.findUserProfileByJwt(token);
////		Order order = orderService.findOrderById(orderId);
////		return new ResponseEntity<Order>(order, HttpStatus.OK);
////	}
//
//	@GetMapping("/{orderId}")
//	public ResponseEntity<Order> getOrderById(@PathVariable("orderId}") Long orderId) {
//		try {
//			System.out.println("Fetching order with ID: " + orderId); // Debug log
//			Order order = orderService.findOrderById(orderId);
//			return ResponseEntity.ok(order);
//		} catch (OrderException e) {
//			System.out.println("Error: " + e.getMessage()); // Debug log
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		}
//	}
//}

package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Address;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String token,
			@RequestBody Address shippingAddress) throws UserException, OrderException {
		User user = userService.findUserProfileByJwt(token);
		Order order = orderService.createOrder(user, shippingAddress);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("Authorization") String token) throws UserException {
		User user = userService.findUserProfileByJwt(token);
		List<Order> allOrders = orderService.usersOrderHishtory(user.getId());
		return new ResponseEntity<>(allOrders, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String token)
			throws UserException, OrderException {
		User user = userService.findUserProfileByJwt(token);
		Order order = orderService.findOrderById(orderId);
//		Order order = orderService.findOrderById(id);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
}
