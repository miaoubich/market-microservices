package org.korcula.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CustomerConfiguration {

	@Value("${productservice.base.url}")
	private String productBaseUrl;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public WebClient webClient() {
		return WebClient
				.builder()
				.baseUrl(productBaseUrl)
				.build();
	}
}
