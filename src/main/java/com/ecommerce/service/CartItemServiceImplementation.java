package com.ecommerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.Exception.CartItemException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.reposatory.CartItemReposatory;
import com.ecommerce.reposatory.CartReposatory;

@Service
public class CartItemServiceImplementation implements CartItemService {

	private CartItemReposatory cartItemReposatory;

	private UserService userServicel;

	private CartReposatory cartReposatory;

	public CartItemServiceImplementation(CartItemReposatory cartItemReposatory, UserService userServicel,
			CartReposatory cartReposatory) {
		super();
		this.cartItemReposatory = cartItemReposatory;
		this.userServicel = userServicel;
		this.cartReposatory = cartReposatory;
	}

	@Override
	public CartItem createCartItem(CartItem cartIem) {
		cartIem.setQuantity(1);
		cartIem.setPrice(cartIem.getProduct().getPrice() * cartIem.getQuantity());
		cartIem.setDiscountedPrice(cartIem.getProduct().getDiscountedPrice() * cartIem.getQuantity());

		CartItem newCart = this.cartItemReposatory.save(cartIem);
		return newCart;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item = findCartItemById(id);
		User user = userServicel.findUserById(item.getUserId());

		if (user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getProduct().getPrice() * item.getQuantity());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
		}

		CartItem updateItem = this.cartItemReposatory.save(item);
		return updateItem;
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem = cartItemReposatory.isCartItemExist(cart, product, null, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long CartItemId) throws CartItemException, UserException {
		CartItem cartItem = findCartItemById(CartItemId);
		User user = userServicel.findUserById(userId);

		if (user == null) {
			throw new UserException("Invalid User Id");
		}

		if (cartItem.getUserId().equals(user.getId())) {
			cartItemReposatory.delete(cartItem);
		}

	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		System.out.println(cartItemId);
		Optional<CartItem> cartItem = cartItemReposatory.findById(cartItemId);
		if (cartItem.isPresent()) {
			return cartItem.get();
		}
		throw new CartItemException("CartItem Not Found");
	}

}
