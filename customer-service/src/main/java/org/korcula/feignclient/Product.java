package org.korcula.feignclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private Long id;
	private String productName;
	private int quantity;
	private double price;
	private Long customerId;
}
