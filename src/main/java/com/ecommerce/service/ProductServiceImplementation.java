//package com.ecommerce.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import com.ecommerce.Exception.ProductException;
//import com.ecommerce.model.Category;
//import com.ecommerce.model.Product;
//import com.ecommerce.reposatory.CategoryReposatory;
//import com.ecommerce.reposatory.ProductReposatory;
//import com.ecommerce.request.CreateProductRequest;
//
//@Service
//public class ProductServiceImplementation implements ProductService {
//
//	@Autowired
//	private ProductReposatory productRepository;
//	@Autowired
//	private CategoryReposatory categoryRepository;
//	@Autowired
//	private UserService userService;
//
//	@Override
//	public Product createProduct(CreateProductRequest req) {
//		Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
//		if (topLevel == null) {
//			topLevel = new Category();
//			topLevel.setName(req.getTopLevelCategory());
//			topLevel.setLevel(1);
//			topLevel = categoryRepository.save(topLevel);
//		}
//
//		Category secondLevel = categoryRepository.findByNameAndParentCategory(req.getSecondLevelCategory(), topLevel);
//		if (secondLevel == null) {
//			secondLevel = new Category();
//			secondLevel.setName(req.getSecondLevelCategory());
//			secondLevel.setLevel(2);
//			secondLevel.setParentCategory(topLevel);
//			secondLevel = categoryRepository.save(secondLevel);
//		}
//
//		Category thirdLevel = categoryRepository.findByNameAndParentCategory(req.getThirdLevelCategory(), secondLevel);
//		if (thirdLevel == null) {
//			thirdLevel = new Category();
//			thirdLevel.setName(req.getThirdLevelCategory());
//			thirdLevel.setLevel(3);
//			thirdLevel.setParentCategory(secondLevel);
//			thirdLevel = categoryRepository.save(thirdLevel);
//		}
//
//		Product product = new Product();
//		product.setTitle(req.getTitle());
//		product.setColor(req.getColor());
//		product.setDescription(req.getDescription());
//		product.setDiscountedPrice(req.getDiscountedPrice());
//		product.setDiscountPercent(req.getDiscountPercent());
//		product.setImageUrl(req.getImageUrl());
//		product.setBrand(req.getBrand());
//		product.setPrice(req.getPrice());
//		product.setSize(req.getSize());
//		product.setQuantity(req.getQuantity());
//		product.setCreatedAt(LocalDateTime.now());
//
//		Product newProduct = new Product();
//		newProduct = productRepository.save(product);
//
//		return newProduct;
//
//	}
//
//	@Override
//	public String deleteProduct(Long productId) throws ProductException {
//
//		Product product = findProductById(productId);
//		product.getSize().clear();
//		productRepository.delete(product);
//		return "product delete Successfully";
//	}
//
//	@Override
//	public Product updateProduct(Long productId, Product req) throws ProductException {
//		Product product = new Product();
//		if (product.getQuantity() != 0) {
//
//			product.setQuantity(req.getQuantity());
//		}
//		return productRepository.save(product);
//	}
//
//	@Override
//	public Product findProductById(Long productId) throws ProductException {
//		Optional<Product> opt = productRepository.findById(productId);
//		if (opt.isPresent()) {
//			return opt.get();
//		}
//
//		throw new ProductException("product Not Found");
//	}
//
//	@Override
//	public List<Product> findProductByCategory(String category) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
//			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//		List<Product> products = productRepository.filterProducts(category, sort, minDiscount, minDiscount,
//				minDiscount);
//
//		if (!colors.isEmpty()) {
//			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
//					.collect(Collectors.toList());
//		}
//
////		if (stock!=null) {
////			if(stock.equals("in_stock")){
////				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());			
////				}
////		}
//		if (stock != null && stock.equals("in_stock")) {
//			products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
//		} else if (stock.equals("out_of_stock")) {
//			products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
//		}
//
//		int startIndex = (int) pageable.getOffset();
//		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
//
//		List<Product> pageContent = products.subList(endIndex, endIndex);
//
//		Page<Product> filteredProduct = new PageImpl<>(pageContent, pageable, products.size());
//
//		return filteredProduct;
//	}
//
//	@Override
//	public List<Product> findAllProducts() {
//		List<Product> allProducts = productRepository.findAll();
//		return allProducts;
//	}
//
//}

package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecommerce.reposatory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.reposatory.CategoryReposatory;

import com.ecommerce.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryReposatory categoryRepository;
	@Autowired
	private UserService userService;

	@Override
	public Product createProduct(CreateProductRequest req) {
		Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
		if (topLevel == null) {
			topLevel = new Category();
			topLevel.setName(req.getTopLevelCategory());
			topLevel.setLevel(1);
			topLevel = categoryRepository.save(topLevel);
		}

		Category secondLevel = categoryRepository.findByNameAndParentCategory(req.getSecondLevelCategory(), topLevel);
		if (secondLevel == null) {
			secondLevel = new Category();
			secondLevel.setName(req.getSecondLevelCategory());
			secondLevel.setLevel(2);
			secondLevel.setParentCategory(topLevel);
			secondLevel = categoryRepository.save(secondLevel);
		}

		Category thirdLevel = categoryRepository.findByNameAndParentCategory(req.getThirdLevelCategory(), secondLevel);
		if (thirdLevel == null) {
			thirdLevel = new Category();
			thirdLevel.setName(req.getThirdLevelCategory());
			thirdLevel.setLevel(3);
			thirdLevel.setParentCategory(secondLevel);
			thirdLevel = categoryRepository.save(thirdLevel);
		}

		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPercent(req.getDiscountPercent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSize(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCreatedAt(LocalDateTime.now());
		product.setCategory(thirdLevel); // Ensure the product is linked to the correct category

		return productRepository.save(product);
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		product.getSize().clear();
		productRepository.delete(product);
		return "Product deleted successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product existingProduct = findProductById(productId);
		if (req.getQuantity() != 0) {
			existingProduct.setQuantity(req.getQuantity());
		}
		// Set other fields from req to existingProduct as necessary
		return productRepository.save(existingProduct);
	}

	@Override
	public Product findProductById(Long productId) throws ProductException {
		Optional<Product> opt = productRepository.findById(productId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found");
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		List<Product> filterProducts = productRepository.filterProductsByCategory(category);
		return filterProducts;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
//			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//		List<Product> products = productRepository.filterProducts(category, sort, minDiscount, minPrice, maxPrice);
//
//		if (!colors.isEmpty()) {
//			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
//					.collect(Collectors.toList());
//		}
//
//		if (stock != null) {
//			if (stock.equals("in_stock")) {
//				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
//			} else if (stock.equals("out_of_stock")) {
//				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
//			}
//		}
//
//		int startIndex = (int) pageable.getOffset();
//		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
//
//		List<Product> pageContent = products.subList(startIndex, endIndex);
//
//		return new PageImpl<>(pageContent, pageable, products.size());

			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		// Ensure parameters passed are correct and match the query definition
		List<Product> products = productRepository.filterProducts(category, sort, maxPrice, minPrice, minDiscount);
		System.out.println("Product ----------------------------->" + products);

		if (!colors.isEmpty()) {
			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}

		if (stock != null) {
			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}
		}

		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

		// Ensure that sublist indices are correct
		List<Product> pageContent = products.subList(startIndex, endIndex);

		return new PageImpl<>(pageContent, pageable, products.size());
	}

	@Override
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}
}
