package org.korcula.feignclient;

import org.korcula.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "productservice", url="http://localhost:9091")
@FeignClient("product-service")
public interface ProductFeignClient { //This is a proxy

	@GetMapping("/products/{customerId}")
	public ResponseEntity<ProductResponse> getProductByCustomerId(@PathVariable("customerId") Integer customerId);
}
