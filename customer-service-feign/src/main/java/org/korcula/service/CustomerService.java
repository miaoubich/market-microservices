package org.korcula.service;

import java.util.List;
import java.util.Map;

import org.korcula.dto.CustomerRequest;
import org.korcula.dto.CustomerResponse;
import org.korcula.model.Customer;

public interface CustomerService {

	String createNewCustomer(CustomerRequest customerDto);
	
	CustomerResponse getOneCustomer(Integer custId);
	
	List<Customer> getAllCustomer();
	
	CustomerResponse editCustomer(Customer customer);
	
	String deleteCustomerById(Integer custId);
	
	CustomerResponse updateByField(Integer custId, Map<Object, Object> fields);
}
