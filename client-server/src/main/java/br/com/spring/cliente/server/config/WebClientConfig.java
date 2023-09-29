package br.com.spring.cliente.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	
	@Bean
	public WebClient createWebClient() {
		return WebClient.builder()
						.baseUrl("http://127.0.0.1:9090")
				      	.build();
	}

}
