package org.korcula;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.korcula.dto.CustomerRequest;
import org.korcula.dto.CustomerResponse;
import org.korcula.dto.ProductResponse;
import org.korcula.service.CustomerService;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceMockMvcTests {

	@Autowired
	MockMvc mockMvc;
	@Mock
	CustomerService customerService;
	
	private Integer custId = 5;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void createNewCustomertest() throws Exception {
		CustomerRequest request = mockCustomerRequest(); 
		String jsonString = mapper.writeValueAsString(request);
		
		String expected_result = "Customer successfuly added to the DB!";
		
		when(customerService.createNewCustomer(any(CustomerRequest.class))).thenReturn(expected_result);
		
		//Body = {"id":1,"customerName":"Ali","email":"ali@mail.org","gender":"male"}
		this.mockMvc.perform(post("/customers").content(jsonString).contentType(MediaType.APPLICATION_JSON))
											   .andDo(print())
											   .andExpect(status().isCreated());
	}
	
	@Test
	public void getCustomerTest() throws Exception {
		CustomerResponse response = mockCustomerResponse(); 
		
		when(customerService.getOneCustomer(any())).thenReturn(response);
		
//		this.mockMvc.perform(get("/customers/" + custId))
//							.andDo(print())
//							.andExpect(status().isOk())
//							.andReturn();
	}

	private CustomerResponse mockCustomerResponse() {
		ProductResponse product = mockProduct();
		
		return CustomerResponse.builder()
							.id(custId)
							.customerName("Customer1")
							.email("customer@mail.org")
							.gender("male")
							.productResponse(product)
							.build();
	}

	private ProductResponse mockProduct() {
		return ProductResponse.builder()
							.productName("Nintendo Switch")
							.quantity(2)
							.price(259.99)
							.build();
	}

	private CustomerRequest mockCustomerRequest() {
		return CustomerRequest.builder()
							.id(1)
							.customerName("Ali")
							.email("ali@mail.org")
							.gender("male")
							.build();
	}
}
