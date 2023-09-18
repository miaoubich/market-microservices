package org.korcula.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProduct {
	private String productName;
	private int quantity;
	private double price;
	private Long customerId;
}
