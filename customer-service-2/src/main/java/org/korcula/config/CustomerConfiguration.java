package org.korcula.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfiguration {

//	@Value("${productservice.base.url}")
//	private String productBaseUrl;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
