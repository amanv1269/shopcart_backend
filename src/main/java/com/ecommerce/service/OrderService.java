package com.ecommerce.service;

import java.util.List;

import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Address;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

public interface OrderService {

	public Order createOrder(User user, Address shippingAddress) throws OrderException, UserException;

	public Order findOrderById(Long orderId) throws OrderException;

	public List<Order> usersOrderHishtory(Long userID);

	public Order placedOrder(Long orderID) throws OrderException;

	public Order shippedOrder(Long orderID) throws OrderException;

	public Order deliverdOrder(Long orderID) throws OrderException;

	public Order canceledOrder(Long orderID) throws OrderException;

	public List<Order> getAllOrders();

	public void deleteOrder(Long orderID) throws OrderException;

	public Order confirmedorder(Long orderID) throws OrderException;

}
