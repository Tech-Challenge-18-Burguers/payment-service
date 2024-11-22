package br.com.eighteenburguers.payment.helper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JsonHelperTest {

	@Test
	void shouldBeConvertToJson() {
		Map<String, String> map = new HashMap<>();
		map.put("key", "value");
		String json = JsonHelper.convertToJson(map);
		assertNotNull(json);
	}
	
	@Test
	@SuppressWarnings({ "unchecked" })
	void shouldBeConvertToObject() {
		String json = "{ \"key\": \"value\" }";
		Map<String, String> map = JsonHelper.convertToObject(json, HashMap.class);
		assertNotNull(map);
	}
	
	@Test
	void shouldBeNotConverter() {
		Object input = new Object();
		assertThrows(IllegalArgumentException.class, () -> JsonHelper.convertToJson(input));
	}
}
