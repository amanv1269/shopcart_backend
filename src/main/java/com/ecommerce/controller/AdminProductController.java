package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.request.CreateProductRequest;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {
	@Autowired
	private ProductService productService;

//	@PostMapping("/")
//	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) {
//		Product product = productService.createProduct(req);
//
//		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
//	}
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) {
		Product product = productService.createProduct(req);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}

	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException {
		productService.deleteProduct(productId);
		ApiResponse res = new ApiResponse();
		res.setMessage("product deleted");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProduct() {
		productService.findAllProducts();
		return new ResponseEntity<List<Product>>(HttpStatus.FOUND);
	}

	@PutMapping("/{product}/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product req, @PathVariable long productId)
			throws ProductException {
		Product product = productService.updateProduct(productId, req);
		return new ResponseEntity<Product>(HttpStatus.ACCEPTED);
	}

	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createProducts(@RequestBody CreateProductRequest[] req) {
		ApiResponse res = new ApiResponse();
		for (CreateProductRequest product : req) {
			productService.createProduct(product);
		}
		res.setMessage("Product created succesfully");
		res.setStatus(true);

		return new ResponseEntity<ApiResponse>(res, HttpStatus.CREATED);
	}
}
