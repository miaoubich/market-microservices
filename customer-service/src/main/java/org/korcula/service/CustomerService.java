package org.korcula.service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final ProductFeignClient productFeignClient;
	private final RestTemplate restTemplate;

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
		
//		ProductResponse product = productFeignClient.getProductByCustomerId(custId);
		ProductResponse productResponse = restTemplate.getForObject("http://localhost:9091/product/api/products/{custId}", ProductResponse.class, custId);
		
		customerResponse.setProductResponse(productResponse);

		if (customer != null)
			BeanUtils.copyProperties(customer, customerResponse);
		return customerResponse;

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
//			existCustomer.setProducts(customer.getProducts());

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
		}
		else
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

//	public Map<String, List<ResponseCustomerAndProduct>> getCustomerInfo() {
//		return customerRepository.getCustomerNameAndProductName().stream().collect(Collectors.groupingBy(ResponseCustomerAndProduct::getCustomerName));
//	}
}
