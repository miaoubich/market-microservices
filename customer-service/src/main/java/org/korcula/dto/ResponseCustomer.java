package org.korcula.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCustomer {

	private String customerName;
	private String email;
	private String gender;
	private List<Long> products;
}
