package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategoryhandler(@RequestParam String category,
			@RequestParam List<String> color, @RequestParam List<String> sizes, @RequestParam Integer minPrice,
			@RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
			@RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {

		Page<Product> res = productService.getAllProduct(category, color, sizes, minPrice, maxPrice, minDiscount, sort,
				stock, pageNumber, pageSize);
		System.out.println("complete products");
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

	}

	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {

		Product product = productService.findProductById(productId);

		return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
	}


	@GetMapping("/products/category")
	ResponseEntity<List<Product>> findProductByCategory(@RequestParam("category") String category) throws ProductException {
		List<Product> products = productService.findProductByCategory(category);
		return new ResponseEntity<List<Product>>(products, HttpStatus.ACCEPTED);
	}

}
