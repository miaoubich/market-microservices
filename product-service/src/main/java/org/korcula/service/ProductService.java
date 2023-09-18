package org.korcula.service;

import java.util.List;
import java.util.stream.Collectors;

import org.korcula.dto.RequestProduct;
import org.korcula.dto.ResponseProduct;
import org.korcula.model.Product;
import org.korcula.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public String newProduct(RequestProduct request) {
		Product newProduct = new Product();
		BeanUtils.copyProperties(request, newProduct);
		System.out.println("Before save: " + newProduct);
		productRepository.save(newProduct);
		String result = "Product successfully added!";
		System.out.println("After save: " + newProduct);
		return result;
	}
	
	public ResponseProduct getProductById(Long productId) {
		Product product = productRepository.findById(productId).orElseGet(null);
		ResponseProduct response = new ResponseProduct();
		
		if(product != null) {
			BeanUtils.copyProperties(product, response);
		}
		return response;
	}
	
	public List<Long> getProducts(){
		List<Product> products = productRepository.findAll();
	
		if(!products.isEmpty())
			return products.stream().map(p -> p.getId()).collect(Collectors.toList());
		else
			throw new NullPointerException("Products are not available!");
	}
	
}
