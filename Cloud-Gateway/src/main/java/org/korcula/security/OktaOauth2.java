package org.korcula.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class OktaOauth2 {

	@Bean
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity serverHttpSecurity) {
		serverHttpSecurity.authorizeExchange()
		    			  .anyExchange().authenticated()
		    			  .and()
		    			  .oauth2Login()
		    			  .and()
		    			  .oauth2ResourceServer()
		    			  .jwt();
		return serverHttpSecurity.build();
	}
}
