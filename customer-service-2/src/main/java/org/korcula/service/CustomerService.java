package org.korcula.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.korcula.dto.CustomerRequest;
import org.korcula.dto.CustomerResponse;
import org.korcula.dto.ProductResponse;
import org.korcula.feignclient.ProductFeignClient;
import org.korcula.model.Customer;
import org.korcula.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final ProductFeignClient productFeignClient;

	public String createNewCustomer(CustomerRequest customerDto) {
		Customer newCustomer = new Customer();
		BeanUtils.copyProperties(customerDto, newCustomer);

		customerRepository.save(newCustomer);

		String result = "Customer successfuly added to the DB!";

		return result;
	}

	public CustomerResponse getOneCustomer(Integer custId) {
		Customer customer = customerRepository.findById(custId).get();
		CustomerResponse customerResponse = new CustomerResponse();

		ProductResponse productResponse = new ProductResponse();

		try {
			ResponseEntity<ProductResponse> productResponseEntity = productFeignClient.getProductByCustomerId(custId);
			productResponse = productResponseEntity.getBody();
		} 
		catch (FeignException e) {
		    if (e.status() == 302) {
		        // Extract the response body as a String
		        String responseBody = e.contentUTF8();
		        
		        // parse the JSON data from the responseBody
		        productResponse = parseJsonResponse(responseBody);
		    } 
		    else 
		    	productResponse = null;
		}
		customerResponse.setProductResponse(productResponse);
		
		if (customer != null)
			BeanUtils.copyProperties(customer, customerResponse);
		return customerResponse;

	}
	
	private ProductResponse parseJsonResponse(String jsonResponse) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.readValue(jsonResponse, ProductResponse.class);
	    } 
	    catch (IOException e) {
	        
	        System.out.println("ERROR -> " + e.getMessage());
	        return null;
	    }
	}

	public List<Customer> getAllCustomer() {
		List<Customer> customers = customerRepository.findAll();

		return customers;
	}

	public CustomerResponse editCustomer(Customer customer) {
		Customer existCustomer = customerRepository.findById(customer.getId()).orElse(null);

		if (existCustomer != null) {
			existCustomer.setCustomerName(customer.getCustomerName());
			existCustomer.setEmail(customer.getEmail());
			existCustomer.setGender(customer.getGender());

			customerRepository.save(existCustomer);
		}
		CustomerResponse responseDto = new CustomerResponse();
		BeanUtils.copyProperties(existCustomer, responseDto);

		return responseDto;
	}

	public String deleteCustomerById(Integer custId) {
		Customer existCustomer = customerRepository.findById(custId).orElse(null);
		String result = "Customer with id = " + custId + " doesn't exist!";
		if (existCustomer != null) {
			customerRepository.delete(existCustomer);
			result = "Customer deleted successfuly!";

			return result;
		} else
			throw new NoSuchElementException();
	}

	public CustomerResponse updateByField(Integer custId, Map<Object, Object> fields) {
		Customer customer = customerRepository.findById(custId).get();

		if (customer != null) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findRequiredField(Customer.class, (String) key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, customer, value);
			});
			customerRepository.save(customer);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id = " + custId + " not found!");

		CustomerResponse customerDto = new CustomerResponse();
		BeanUtils.copyProperties(customer, customerDto);

		return customerDto;
	}

}
