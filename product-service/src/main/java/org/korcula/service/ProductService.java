package org.korcula.service;

import java.util.List;
import java.util.stream.Collectors;

import org.korcula.dto.RequestProduct;
import org.korcula.dto.ProductResponse;
import org.korcula.model.Product;
import org.korcula.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	public String newProduct(RequestProduct request) {

		Product newProduct = modelMapper.map(request, Product.class);
		productRepository.save(newProduct);
		
		String result = "Product successfully added!";
		return result;
	}
	
	public ProductResponse getProductById(Integer productId) {
		Product product = productRepository.findById(productId).orElseGet(null);
		ProductResponse response = new ProductResponse();
		
		if(product != null) {
			BeanUtils.copyProperties(product, response);
		}
		return response;
	}
	
	public ProductResponse getProductByCustomerId(Integer customerId) {
		Product product = productRepository.findProductByCustomerId(customerId);
		ProductResponse responseProduct = modelMapper.map(product, ProductResponse.class);
	
		return responseProduct;
	}
	
	public List<Integer> getProducts(){
		List<Product> products = productRepository.findAll();
	
		if(!products.isEmpty())
			return products.stream().map(p -> p.getId()).collect(Collectors.toList());
		else
			throw new NullPointerException("Products are not available!");
	}
	
}
