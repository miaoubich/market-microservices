package org.korcula.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "customer_name")
	private String customerName;
	private String email;
	private String gender;
	
//	@ElementCollection
//	@CollectionTable(name = "customer_product_ids",
//					 joinColumns = @JoinColumn(name = "customer_id"))
//	@Column(name = "product_id")
//	private List<Long> products;
}
