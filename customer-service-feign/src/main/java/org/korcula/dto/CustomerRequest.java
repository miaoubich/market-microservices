package org.korcula.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

	private Integer id;
	private String customerName;
	private String email;
	private String gender;
	
//	@ElementCollection
//	@CollectionTable(name = "customer_product_ids",
//					 joinColumns = @JoinColumn(name = "customer_id"))
//	@Column(name = "product_id")
//	private List<Long> products;
}
