package org.korcula.controller;

import java.util.List;
import java.util.Map;

import org.korcula.dto.RequestCustomer;
import org.korcula.dto.ResponseCustomer;
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
@RequestMapping("/api/market")
public class CustomerController {



	private final CustomerService customerService;
	
	@PostMapping
	public ResponseEntity<String> newCustomer(@RequestBody RequestCustomer customer){
		String resultMessage = customerService.createNewCustomer(customer);
		return new ResponseEntity<String>(resultMessage, HttpStatus.CREATED);
	}
	
	@GetMapping("/{custId}")
	public ResponseEntity<ResponseCustomer> getCustomer(@PathVariable Long custId){
		ResponseCustomer customer = customerService.getOneCustomer(custId);
		return new ResponseEntity<ResponseCustomer>(customer, HttpStatus.FOUND);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Customer>> printAllCustomer(){
		List<Customer> customers = customerService.getAllCustomer();
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.FOUND);
	}
	
	@PutMapping
	public ResponseEntity<ResponseCustomer> updateCustomer(@RequestBody Customer customer){
		ResponseCustomer updatedCustomer = customerService.editCustomer(customer);
		return new ResponseEntity<ResponseCustomer>(updatedCustomer, HttpStatus.OK);
	}
	
	@DeleteMapping("/{custId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long custId) {
		String result = customerService.deleteCustomerById(custId);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@PatchMapping("/{custId}")
	public ResponseEntity<ResponseCustomer> updateCustomerByField(@PathVariable Long custId, @RequestBody Map<Object, Object> fields){
		ResponseCustomer customCustomer = customerService.updateByField(custId, fields);
		return new ResponseEntity<ResponseCustomer>(customCustomer, HttpStatus.OK);
	} 
	
//	@GetMapping("/getcutprod")
//	public ResponseEntity<?> customerNameAndProductName(){
//		Map<String, List<ResponseCustomerAndProduct>> response = customerService.getCustomerInfo();
//		return new ResponseEntity<>(response, HttpStatus.FOUND);
//	}
}
