package org.korcula.controller;

import java.util.List;
import java.util.Map;

import org.korcula.dto.CustomerRequest;
import org.korcula.dto.CustomerResponse;
import org.korcula.model.Customer;
import org.korcula.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;
	
	@PostMapping
	public ResponseEntity<String> newCustomer(@RequestBody CustomerRequest customer){
		String resultMessage = customerService.createNewCustomer(customer);
		return new ResponseEntity<String>(resultMessage, HttpStatus.CREATED);
	}
	
	@GetMapping("/{custId}")
	public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Integer custId){
		CustomerResponse customer = customerService.getOneCustomer(custId);
		return new ResponseEntity<CustomerResponse>(customer, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Customer>> printAllCustomer(){
		List<Customer> customers = customerService.getAllCustomer();
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.FOUND);
	}
	
	@PutMapping
	public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody Customer customer){
		CustomerResponse updatedCustomer = customerService.editCustomer(customer);
		return new ResponseEntity<CustomerResponse>(updatedCustomer, HttpStatus.OK);
	}
	
	@DeleteMapping("/{custId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer custId) {
		String result = customerService.deleteCustomerById(custId);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@PatchMapping("/{custId}")
	public ResponseEntity<CustomerResponse> updateCustomerByField(@PathVariable Integer custId, @RequestBody Map<Object, Object> fields){
		CustomerResponse customCustomer = customerService.updateByField(custId, fields);
		return new ResponseEntity<CustomerResponse>(customCustomer, HttpStatus.OK);
	} 
	
//	@GetMapping("/getcutprod")
//	public ResponseEntity<?> customerNameAndProductName(){
//		Map<String, List<ResponseCustomerAndProduct>> response = customerService.getCustomerInfo();
//		return new ResponseEntity<>(response, HttpStatus.FOUND);
//	}
}
