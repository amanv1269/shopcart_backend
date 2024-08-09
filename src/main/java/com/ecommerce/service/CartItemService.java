package com.ecommerce.service;

import com.ecommerce.Exception.CartItemException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartIem);

	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

	public void removeCartItem(Long userId, Long CartItemId) throws CartItemException, UserException;

	public CartItem findCartItemById(Long CartItemId) throws CartItemException;
}
