package org.korcula.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestProduct {

	private Integer id;
	@Column(name = "product_name")
	private String productName;
	private int quantity;
	private double price;
	
	//Reference to Customer entity using customer_id
//	@Column(name = "customer_id")
//	private Long customerId;
}
