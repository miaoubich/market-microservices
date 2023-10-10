package org.korcula.feignclient;

import org.korcula.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

//	@GetMapping("/product/api/products/{customerId}")
	@GetMapping("/products/{customerId}")
	public ProductResponse getProductByCustomerId(@PathVariable Integer customerId);
}
