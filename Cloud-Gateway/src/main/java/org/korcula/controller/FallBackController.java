package org.korcula.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

	@GetMapping("/customerServiceFallBack")
	public String customerServiceFallBack() {
		return "Customer Service is down!";
	}
	
	@GetMapping("/productServiceFallBack")
	public String productServiceFallBack(){
		return "Product Service is down!";
	}
}
