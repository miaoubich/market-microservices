package org.korcula.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

	@GetMapping("/api/product")
	public List<Long> getAllProducts();
}
