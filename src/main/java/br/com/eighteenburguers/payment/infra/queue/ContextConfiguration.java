package br.com.eighteenburguers.payment.infra.queue;

import java.util.HashMap;
import java.util.Map;

public class ContextConfiguration {

	private final Map<String, Class<?>> configuration;
	
	public ContextConfiguration() {
		this.configuration = new HashMap<>();
	}
	
	public void configure(String context, Class<?> classType) {
		this.configuration.put(context, classType);
	}
	
	public Class<?> getContext(final String context) {
		return this.configuration.get(context);
	}
}
