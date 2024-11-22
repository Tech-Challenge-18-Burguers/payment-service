package br.com.eighteenburguers.payment.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonHelper {

	private static final ObjectMapper MAPPER;
	
	static {
		MAPPER = new ObjectMapper();
		MAPPER.registerModule(new JavaTimeModule());
	}
	
	public static final String convertToJson(Object value) {
		try {
			return MAPPER.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static final <T> T convertToObject(String json, Class<T> classType) {
		try {
			return MAPPER.readValue(json, classType);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
