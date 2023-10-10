package org.korcula.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

	private Integer id;
	private String customerName;
	private String email;
	private String gender;
	private ProductResponse productResponse;
	
}
