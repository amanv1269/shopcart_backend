//package com.ecommerce.service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.stereotype.Service;
//
//import com.ecommerce.Exception.OrderException;
//import com.ecommerce.Exception.UserException;
//import com.ecommerce.model.Address;
//import com.ecommerce.model.Cart;
//import com.ecommerce.model.CartItem;
//import com.ecommerce.model.Order;
//import com.ecommerce.model.OrderItem;
//import com.ecommerce.model.User;
//import com.ecommerce.reposatory.AddressReposatory;
//import com.ecommerce.reposatory.CartReposatory;
//import com.ecommerce.reposatory.OrderItemReposatory;
//import com.ecommerce.reposatory.OrderReposatory;
//import com.ecommerce.reposatory.UserReposatory;
//
//@Service
//public class OrderServiceImplementation implements OrderService {
//
//	private OrderReposatory orderReposatory;
//	private CartReposatory cartReposatory;
//	private AddressReposatory addressReposatory;
//	private UserReposatory userReposatory;
//	private OrderItemService orderItemService;
//	private OrderItemReposatory orderItemReposatory;
//	private CartService cartService;
//
//	public OrderServiceImplementation(CartService cartService, OrderReposatory orderReposatory,
//			CartReposatory cartReposatory, AddressReposatory addressReposatory, UserReposatory userReposatory,
//			OrderItemService orderItemService, OrderItemReposatory orderItemReposatory) {
//		super();
//		this.cartService = cartService;
//		this.orderReposatory = orderReposatory;
//		this.cartReposatory = cartReposatory;
//		this.addressReposatory = addressReposatory;
//		this.userReposatory = userReposatory;
//		this.orderItemService = orderItemService;
//		this.orderItemReposatory = orderItemReposatory;
//	}
//
//	@Override
////	public Order createOrder(User user, Address shippingAddress) throws UserException {
////		shippingAddress.setUser(user);
////		Address address = new Address();
////		user.getAddress().add(address);
////		userReposatory.save(user);
////
////		Cart cart = cartService.finduserCart(user.getId());
////		List<OrderItem> orderItems = new ArrayList<>();
////
////		for (CartItem item : cart.getCartItems()) {
////			OrderItem orderItem = new OrderItem();
////
////			orderItem.setPrice(item.getPrice());
////			orderItem.setProduct(item.getProduct());
////			orderItem.setQuantity(item.getQuantity());
////			orderItem.setSize(item.getSize());
////			orderItem.setUserId(item.getUserId());
////			orderItem.setDiscountedPrice(item.getDiscountedPrice());
////
////			OrderItem createItem = orderItemReposatory.save(orderItem);
////			orderItems.add(createItem);
////		}
////
////		Order createOrder = new Order();
////		createOrder.setUser(user);
////		createOrder.setOrderItems(orderItems);
////		createOrder.setTotalPrice(cart.getTotalPrice());
////		createOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
////		createOrder.setDiscount(cart.getDiscount());
////		createOrder.setTotalItem(cart.getTotalItem());
////		createOrder.setShippingAddress(address);
////		createOrder.setCreatedAt(LocalDateTime.now());
////		createOrder.setOrderStatus("PENDING");
////		createOrder.getPaymentDetails().setStatus("PENDING");
////		createOrder.setOrderDate(LocalDateTime.now());
////		Order newOrder = orderReposatory.save(createOrder);
////
////		for (OrderItem item : orderItems) {
////			item.setOrder(newOrder);
////			orderItemReposatory.save(item);
////		}
////		return newOrder;
////	}
//	public Order createOrder(User user, Address shippingAddress) throws UserException {
//		// Set user to address and save the address first
//		shippingAddress.setUser(user);
//		Address savedAddress = addressReposatory.save(shippingAddress);
//
//		// Get user's cart
//		Cart cart = cartService.finduserCart(user.getId());
//		List<OrderItem> orderItems = new ArrayList<>();
//
//		// Create OrderItems from CartItems
//		for (CartItem item : cart.getCartItems()) {
//			OrderItem orderItem = new OrderItem();
//			orderItem.setPrice(item.getPrice());
//			orderItem.setProduct(item.getProduct());
//			orderItem.setQuantity(item.getQuantity());
//			orderItem.setSize(item.getSize());
//			orderItem.setUserId(item.getUserId());
//			orderItem.setDiscountedPrice(item.getDiscountedPrice());
//
//			OrderItem createdItem = orderItemReposatory.save(orderItem);
//			orderItems.add(createdItem);
//		}
//
//		// Create the Order and associate the saved address
//		Order createOrder = new Order();
//		createOrder.setUser(user);
//		createOrder.setOrderItems(orderItems);
//		createOrder.setTotalPrice(cart.getTotalPrice());
//		createOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
//		createOrder.setDiscount(cart.getDiscount());
//		createOrder.setTotalItem(cart.getTotalItem());
//		createOrder.setShippingAddress(savedAddress); // Use the saved address
//		createOrder.setCreatedAt(LocalDateTime.now());
//		createOrder.setOrderStatus("PENDING");
//		createOrder.getPaymentDetails().setStatus("PENDING");
//		createOrder.setOrderDate(LocalDateTime.now());
//
//		// Save the order
//		Order newOrder = orderReposatory.save(createOrder);
//
//		// Associate OrderItems with the created Order
//		for (OrderItem item : orderItems) {
//			item.setOrder(newOrder);
//			orderItemReposatory.save(item);
//		}
//		return newOrder;
//	}
//
//	// Other methods remain the same
//
//	@Override
//	public Order findOrderById(Long orderId) throws OrderException {
//		Optional<Order> opt = orderReposatory.findById(orderId);
//		if (opt.isPresent()) {
//			return opt.get();
//		}
//		throw new OrderException("Order Not Found");
//	}
//
//	@Override
//	public List<Order> usersOrderHishtory(Long userID) {
//
//		List<Order> order = orderReposatory.getUserOrder(userID);
//		return order;
//	}
//
//	@Override
//	public Order placedOrder(Long orderID) throws OrderException {
//		Order order = findOrderById(orderID);
//		order.setOrderStatus("PLACED");
//		order.getPaymentDetails().setStatus("COMPLETED");
//		return orderReposatory.save(order);
//	}
//
//	@Override
//	public Order shippedOrder(Long orderID) throws OrderException {
//		Order order = findOrderById(orderID);
//		order.setOrderStatus("SHIPPED");
//		return orderReposatory.save(order);
//	}
//
//	@Override
//	public Order deliverdOrder(Long orderID) throws OrderException {
//		Order order = findOrderById(orderID);
//		order.setOrderStatus("DELIVERED");
//		return orderReposatory.save(order);
//	}
//
//	@Override
//	public Order canceledOrder(Long orderID) throws OrderException {
//		Order order = findOrderById(orderID);
//		order.setOrderStatus("CANCELLED");
//		return orderReposatory.save(order);
//	}
//
//	@Override
//	public Order placeDOrder(Long orderID) throws OrderException {
//		Order order = findOrderById(orderID);
//		order.setOrderStatus("PLACED");
//		return orderReposatory.save(order);
//
//	}
//
//	@Override
//	public List<Order> getAllOrders() {
//		List<Order> order = orderReposatory.findAll();
//		return order;
//	}
//
//	@Override
//	public void deleteOrder(Long orderID) throws OrderException {
//		Order order = findOrderById(orderID);
//
//		orderReposatory.deleteById(orderID);
//
//	}
//
//	@Override
//	public Order confirmedorder(Long orderID) throws OrderException {
//		Order order = findOrderById(orderID);
//		order.setOrderStatus("CONFIRMED");
//		return orderReposatory.save(order);
//	}
//
//}

package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Address;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.User;
import com.ecommerce.reposatory.AddressReposatory;
import com.ecommerce.reposatory.CartReposatory;
import com.ecommerce.reposatory.OrderItemReposatory;
import com.ecommerce.reposatory.OrderReposatory;
import com.ecommerce.reposatory.UserReposatory;

@Service
public class OrderServiceImplementation implements OrderService {

	private final OrderReposatory orderRepository;
	private final CartReposatory cartRepository;
	private final AddressReposatory addressRepository;
	private final UserReposatory userRepository;
	private final OrderItemService orderItemService;
	private final OrderItemReposatory orderItemRepository;
	private final CartService cartService;

	public OrderServiceImplementation(CartService cartService, OrderReposatory orderRepository,
			CartReposatory cartRepository, AddressReposatory addressRepository, UserReposatory userRepository,
			OrderItemService orderItemService, OrderItemReposatory orderItemRepository) {
		this.cartService = cartService;
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.orderItemService = orderItemService;
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public Order createOrder(User user, Address shippingAddress) throws OrderException, UserException {
		Address address = null;
		User userByAddressId = userRepository.findUserByAddressId(shippingAddress.getId());
//		Address savedAddress = addressRepository.save(shippingAddress);
//		if (userByAddressId.getId() != user.getId()) {
//			shippingAddress.setUser(user);
//			address = addressRepository.save(shippingAddress);
//			user.getAddress().add(address);
//			userRepository.save(user);
//		}
		if (userByAddressId == null || userByAddressId.getId() != user.getId()) {
			// No user found or the user does not match, create a new address
			shippingAddress.setUser(user);
			address = addressRepository.save(shippingAddress);
			user.getAddress().add(address);
			userRepository.save(user);
		}

		else {
			Address address1 = new Address();
			address1.setFirstName(shippingAddress.getFirstName());
			address1.setLastName(shippingAddress.getLastName());
			address1.setCity(shippingAddress.getCity());
			address1.setMobile(shippingAddress.getMobile());
			address1.setState(shippingAddress.getState());
			address1.setStreetAddress(shippingAddress.getStreetAddress());
			address1.setUser(user);
			address1.setZipCode(shippingAddress.getZipCode());
			address1.setMobile(shippingAddress.getMobile());
			address = addressRepository.save(address1);
		}

		Cart cart = cartService.finduserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();

		// Create OrderItems from CartItems
		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());

			OrderItem createdItem = orderItemRepository.save(orderItem);
			orderItems.add(createdItem);
		}

		// Create the Order and associate the saved address
		Order createOrder = new Order();
		createOrder.setUser(user);
		createOrder.setOrderItems(orderItems);
		createOrder.setTotalPrice(cart.getTotalPrice());
		createOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createOrder.setDiscount(cart.getDiscount());
		createOrder.setTotalItem(cart.getTotalItem());
//		createOrder.setShippingAddress(savedAddress);
		if (address != null) {
			createOrder.setShippingAddress(address);
		} else {
			createOrder.setShippingAddress(shippingAddress);
		} // Use the saved address
		createOrder.setCreatedAt(LocalDateTime.now());
		createOrder.setDeliverDate(LocalDateTime.now().plusDays(5));
		createOrder.setOrderStatus("PENDING");
		createOrder.getPaymentDetails().setStatus("PENDING");
		createOrder.setOrderDate(LocalDateTime.now());

		// Save the order
		Order newOrder = orderRepository.save(createOrder);

		// Associate OrderItems with the created Order
		for (OrderItem item : orderItems) {
			item.setOrder(newOrder);
			orderItemRepository.save(item);
		}
		System.out.println("Order-------------->" + createOrder);
		return newOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> optional = orderRepository.findById(orderId);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new OrderException("Order is not present with this id");
	}

	@Override
	public List<Order> usersOrderHishtory(Long userID) {
		return orderRepository.getUserOrder(userID);
	}

	@Override
	public Order placedOrder(Long orderID) throws OrderException {
		Order order = findOrderById(orderID);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderID) throws OrderException {
		Order order = findOrderById(orderID);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
	}

	@Override
	public Order deliverdOrder(Long orderID) throws OrderException {
		Order order = findOrderById(orderID);
		order.setOrderStatus("DELIVERED");
		return orderRepository.save(order);
	}

	@Override
	public Order canceledOrder(Long orderID) throws OrderException {
		Order order = findOrderById(orderID);
		order.setOrderStatus("CANCELLED");
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderID) throws OrderException {
		findOrderById(orderID);
		orderRepository.deleteById(orderID);
	}

	@Override
	public Order confirmedorder(Long orderID) throws OrderException {
		Order order = findOrderById(orderID);
		order.setOrderStatus("CONFIRMED");
		return orderRepository.save(order);
	}

}
