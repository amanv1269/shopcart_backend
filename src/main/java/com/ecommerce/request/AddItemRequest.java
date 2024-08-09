package com.ecommerce.request;

public class AddItemRequest {

	private Long productId;
	private String size;
	private int quantity;
	private int price;

	public AddItemRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddItemRequest(Long productId, String size, int quantity, int price) {
		super();
		this.productId = productId;
		this.size = size;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
