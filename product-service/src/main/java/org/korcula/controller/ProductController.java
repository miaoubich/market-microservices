package org.korcula.controller;

import java.util.List;

import org.korcula.dto.RequestProduct;
import org.korcula.dto.ProductResponse;
import org.korcula.model.Product;
import org.korcula.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<?> addnewProduct(@RequestBody RequestProduct request){
		String message = productService.newProduct(request);
		
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Integer productId){
		ProductResponse product = productService.getProductById(productId);
		
		return new ResponseEntity<>(product, HttpStatus.FOUND);
	}
	
	@GetMapping
	public List<Integer> getAllProducts(){
		List<Integer> products = productService.getProducts();
		
		return products;
	}
	
	@GetMapping("/customerId")
	public ResponseEntity<ProductResponse> getProductByCustomerId(@PathVariable Integer customerId){
		ProductResponse product = productService.getProductByCustomerId(customerId);
		
		return ResponseEntity.status(HttpStatus.FOUND).body(product);
	}
}
