package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.reposatory.CartReposatory;
import com.ecommerce.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService {

	private CartReposatory cartReposatory;
	private CartItemService cartItemService;
	private ProductService productService;
	@Autowired
	private UserService userService;

	public CartServiceImplementation(CartReposatory cartReposatory, CartItemService cartItemService,
			ProductService productService) {
		super();
		this.cartReposatory = cartReposatory;
		this.cartItemService = cartItemService;
		this.productService = productService;
	}

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cartReposatory.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {

		Cart cart = cartReposatory.findByUserId(userId);

		Product product = productService.findProductById(req.getProductId());

		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		if (isPresent == null) {
			CartItem cartItem = new CartItem();

			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setSize(req.getSize());
			cartItem.setUserId(userId);

			int price = req.getQuantity() * req.getPrice();
			cartItem.setPrice(price);
//			cartItem.setSize(req.getSize());

			CartItem addItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(addItem);
			return "Item Added To Cart";
		}
		isPresent.setQuantity(isPresent.getQuantity() + 1);
		return "item quantity increased in cart";

	}

//	@Override
//	public Cart finduserCart(Long userId) {
//		Cart cart = new Cart();
//		int totalPrice = 0;
//		int totalDiscount = 0;
//		int item = 0;
//
//		for (CartItem cartItem : cart.getCartItems()) {
//
//			totalPrice = totalPrice + cartItem.getPrice();
//			totalDiscount = totalDiscount + cartItem.getDiscountedPrice();
//			item = item + cartItem.getQuantity();
//
//		}
//
//		cart.setTotalItem(item);
//		cart.setDiscount(totalDiscount);
//		cart.setTotalPrice(totalDiscount);
//		cart.setDiscount(totalPrice - totalDiscount);
//
//		return cartReposatory.save(cart);
//
//	}

	@Override
	public Cart finduserCart(Long userId) throws UserException {
		Cart cart = cartReposatory.findByUserId(userId);
		if (cart == null) {
			// If no cart exists for the user, create a new one
			User user = userService.findUserById(userId); // Make sure this method exists in userService
			cart = new Cart();
			cart.setUser(user);
			cart = cartReposatory.save(cart);
		}

		int totalPrice = 0;
		int totalDiscount = 0;
		int item = 0;

		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice += cartItem.getPrice();
			totalDiscount += cartItem.getDiscountedPrice();
			item += cartItem.getQuantity();
		}

		cart.setTotalItem(item);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice - totalDiscount);
		cart.setTotalDiscountedPrice(totalDiscount);

		return cartReposatory.save(cart);
	}

}
