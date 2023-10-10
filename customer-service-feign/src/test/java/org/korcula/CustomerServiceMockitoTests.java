package org.korcula;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.korcula.dto.CustomerResponse;
import org.korcula.dto.ProductResponse;
import org.korcula.feignclient.ProductFeignClient;
import org.korcula.model.Customer;
import org.korcula.repository.CustomerRepository;
import org.korcula.service.CustomerService;
import org.korcula.service.CustomerServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class CustomerServiceMockitoTests {

	@Mock
	private CustomerRepository customerRepository;
	@Mock	
	private ProductFeignClient productFeignClient;
	
	@InjectMocks
	CustomerService customerService = new CustomerServiceImpl(); 

	@DisplayName("Get a single Customer details")
	@Test
	public void getOneCustomerTest() {
		//Mocking
		ResponseEntity<ProductResponse> productResponse = getMockProductResponse(); 
		Optional<Customer> customer = getMockCustomer();
		
		Mockito.when(customerRepository.findById(any())).thenReturn(customer);
		Mockito.when(productFeignClient.getProductByCustomerId(any())).thenReturn(productResponse);
		
		CustomerResponse expectedResult = new CustomerResponse();
		BeanUtils.copyProperties(customer, expectedResult);
		//Actual
		CustomerResponse actualResult = customerService.getOneCustomer(1);
		
		//Verification
		Mockito.verify(customerRepository, times(1)).findById(any());
		Mockito.verify(productFeignClient, times(1)).getProductByCustomerId(any());
		
		//Assert
		Assertions.assertEquals(actualResult, expectedResult);
	}
//org.opentest4j.AssertionFailedError: expected: <CustomerResponse(customerName=Ali, email=ali@gmail.org, gender=Male, productResponse=ProductResponse(productName=Baby Shark, quantity=21, price=45.0))>
	//but was: <CustomerResponse(customerName=null, email=null, gender=null, productResponse=null)>

	private Optional<Customer> getMockCustomer() {
		return Optional.ofNullable(Customer.builder()
					.id(1)
					.customerName("Ali")
					.email("ali@gmail.org")
					.gender("Male")
					.build());
	}

	private ResponseEntity<ProductResponse> getMockProductResponse() {
		ProductResponse productResponse= ProductResponse.builder()
					.productName("Baby Shark")
					.price(45.0)
					.quantity(21)
					.build();
		return ResponseEntity.ok().body(productResponse);
	}
}
