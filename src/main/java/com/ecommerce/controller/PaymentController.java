package com.ecommerce.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.OrderException;
import com.ecommerce.model.Order;
import com.ecommerce.reposatory.OrderReposatory;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.response.PaymentLinkResoponse;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api")
public class PaymentController {

	@Value("${razorpay.api.key}")
	String apiKey;

	@Value("${razorpay.api.secret}")
	String secret;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderReposatory orderReposatory;

//	@PostMapping("/payment/{orderId}")
//	public ResponseEntity<PaymentLinkResoponse> createPaymentLink(@PathVariable long orderId,
//			@RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {
//		Order order = orderService.findOrderById(orderId);
//
//		try {
//			RazorpayClient clien = new RazorpayClient(apiKey, secret);
//
//			JSONObject paymentLinkRequest = new JSONObject();
//
//			paymentLinkRequest.put("amount", order.getTotalPrice() * 100);
//			paymentLinkRequest.put("currency", "INR");
//
//			JSONObject customer = new JSONObject();
//			customer.put("name", order.getUser().getEmail());
//			customer.put("name", order.getUser().getFirstName());
//			paymentLinkRequest.put("Customer", customer);
//
//			JSONObject notify = new JSONObject();
//			notify.put("sms", true);
//			notify.put("email", true);
//			paymentLinkRequest.put("notify", notify);
//
//			paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/" + orderId);
//
//			PaymentLink payment = clien.paymentLink.create(paymentLinkRequest);
//			String paymentLinkId = payment.get("id");
//			String paymentLinkUrl = payment.get("short_url");
//
//			PaymentLinkResoponse res = new PaymentLinkResoponse();
//			res.setPayment_link_id(paymentLinkUrl);
//			res.setPayment_link_url(paymentLinkUrl);
//			return new ResponseEntity<PaymentLinkResoponse>(res, HttpStatus.CREATED);
//
//		} catch (Exception e) {
//			throw new RazorpayException(e.getMessage());
//		}
//	}

	@PostMapping("/payment/{orderId}")
	public ResponseEntity<PaymentLinkResoponse> createPaymentLink(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {

		Order order = orderService.findOrderById(orderId);

		if (order == null) {
			throw new OrderException("Order not found");
		}

		try {
			RazorpayClient client = new RazorpayClient(apiKey, secret);

			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", order.getTotalPrice() * 100);
			paymentLinkRequest.put("currency", "INR");

			JSONObject customer = new JSONObject();
			customer.put("email", order.getUser().getEmail());
			customer.put("name", order.getUser().getFirstName());
			paymentLinkRequest.put("customer", customer);

			JSONObject notify = new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);

			paymentLinkRequest.put("callback_url", "https://shopcart-ivory.vercel.app/" + orderId);
			paymentLinkRequest.put("callback_method", "get");

			PaymentLink payment = client.paymentLink.create(paymentLinkRequest);
			String paymentLinkId = payment.get("id");
			String paymentLinkUrl = payment.get("short_url");

			PaymentLinkResoponse res = new PaymentLinkResoponse();
			res.setPayment_link_id(paymentLinkId);
			res.setPayment_link_url(paymentLinkUrl);
			return new ResponseEntity<>(res, HttpStatus.CREATED);

		} catch (Exception e) {
			throw new RazorpayException(e.getMessage());
		}
	}

	@GetMapping("/payments")
	public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
			@RequestParam(name = "order_id") Long orderId) throws OrderException, RazorpayException {
		RazorpayClient razorpay = new RazorpayClient(apiKey, secret);
		Order order = orderService.findOrderById(orderId);

		try {
			Payment payment = razorpay.payments.fetch(paymentId);
			if (payment.get("status").equals("captured")) {
				order.getPaymentDetails().setPaymentId(paymentId);
				order.getPaymentDetails().setStatus("COMPLETED");
				order.setOrderStatus("PLACED");
				orderReposatory.save(order);
			}
			ApiResponse res = new ApiResponse();
			res.setMessage("Your order Placed");
			res.setStatus(true);
			return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			throw new RazorpayException(e.getMessage());
		}

	}
}
