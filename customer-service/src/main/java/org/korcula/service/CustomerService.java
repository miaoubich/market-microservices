package org.korcula.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.korcula.dto.RequestCustomer;
import org.korcula.dto.ResponseCustomer;
import org.korcula.feignclient.ProductFeignClient;
import org.korcula.model.Customer;
import org.korcula.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final ProductFeignClient productFeignClient;

	public String createNewCustomer(RequestCustomer customerDto) {
		Customer newCustomer = new Customer();
		BeanUtils.copyProperties(customerDto, newCustomer);
		
		List<Long> productIds = productFeignClient.getAllProducts();
		newCustomer.setProducts(productIds);

		customerRepository.save(newCustomer);
		
		String result = "Customer successfuly added to the DB!";

		return result;
	}

	public ResponseCustomer getOneCustomer(Long custId) {
		Customer customer = customerRepository.findById(custId).get();
		ResponseCustomer responseDto = new ResponseCustomer();

		if (customer != null)
			BeanUtils.copyProperties(customer, responseDto);
		return responseDto;

	}

	public List<Customer> getAllCustomer() {
		List<Customer> customers = customerRepository.findAll();

		return customers;
	}

	public ResponseCustomer editCustomer(Customer customer) {
		Customer existCustomer = customerRepository.findById(customer.getId()).orElse(null);

		if (existCustomer != null) {
			existCustomer.setCustomerName(customer.getCustomerName());
			existCustomer.setEmail(customer.getEmail());
			existCustomer.setGender(customer.getGender());
			existCustomer.setProducts(customer.getProducts());

			customerRepository.save(existCustomer);
		}
		ResponseCustomer responseDto = new ResponseCustomer();
		BeanUtils.copyProperties(existCustomer, responseDto);

		return responseDto;
	}

	public String deleteCustomerById(Long custId) {
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

	public ResponseCustomer updateByField(Long custId, Map<Object, Object> fields) {
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

		ResponseCustomer customerDto = new ResponseCustomer();
		BeanUtils.copyProperties(customer, customerDto);
		
		return customerDto;
	}

//	public Map<String, List<ResponseCustomerAndProduct>> getCustomerInfo() {
//		return customerRepository.getCustomerNameAndProductName().stream().collect(Collectors.groupingBy(ResponseCustomerAndProduct::getCustomerName));
//	}
}
