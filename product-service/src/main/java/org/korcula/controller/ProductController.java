package org.korcula.controller;

import java.util.List;

import org.korcula.dto.RequestProduct;
import org.korcula.dto.ResponseProduct;
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
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<?> addnewProduct(@RequestBody RequestProduct request){
		String message = productService.newProduct(request);
		
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Long productId){
		ResponseProduct product = productService.getProductById(productId);
		
		return new ResponseEntity<>(product, HttpStatus.FOUND);
	}
	
	@GetMapping
	public List<Long> getAllProducts(){
		List<Long> products = productService.getProducts();
		
		return products;
	}
}
