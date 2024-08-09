package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.request.AddItemRequest;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.CartService;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<Cart> getUserCart(@RequestHeader("Authorization") String token) throws UserException {
		User user = userService.findUserProfileByJwt(token);
		Cart cart = cartService.finduserCart(user.getId());
		System.out.println(cart);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

//	@PostMapping("/add")
//	public ResponseEntity<ApiResponse> addItemCart(@RequestHeader AddItemRequest req,
//			@RequestHeader("Authorization") String token) throws UserException, ProductException {
//		User user = userService.findUserProfileByJwt(token);
//		cartService.addCartItem(user.getId(), req);
//
//		ApiResponse res = new ApiResponse();
//		res.setMessage("item Added to Cart");
//		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
//	}

	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String token) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(token);
		cartService.addCartItem(user.getId(), req);

		ApiResponse res = new ApiResponse();
		res.setMessage("Item added to cart");
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}

}
