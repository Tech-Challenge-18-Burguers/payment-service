package br.com.eighteenburguers.payment.infra.queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageContextConfiguration {

	@Bean
	ContextConfiguration contextConfiguration() {
		ContextConfiguration configuration = new ContextConfiguration();
		return configuration;
	}
}
