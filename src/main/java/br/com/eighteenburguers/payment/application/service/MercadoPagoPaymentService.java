package br.com.eighteenburguers.payment.application.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import br.com.eighteenburguers.payment.core.entity.MercadoPagoPaymentMethod;
import br.com.eighteenburguers.payment.core.entity.PaymentMethod;
import br.com.eighteenburguers.payment.core.entity.PaymentStatus;
import br.com.eighteenburguers.payment.core.exception.BusinessException;
import br.com.eighteenburguers.payment.core.service.PaymentService;

public class MercadoPagoPaymentService implements PaymentService {

	private final static String QRCODE_FIELD = "qrcode";
	private static final String EXPIRATION_FIELD = "expireIn";
	
	@Override
	public PaymentMethod create(Integer quantityItems, BigDecimal amount) throws BusinessException {
		MercadoPagoPaymentMethod paymentMethod = new MercadoPagoPaymentMethod();
		
		HashMap<String, String> information = new HashMap<>();
		information.put(QRCODE_FIELD, UUID.randomUUID().toString());
		information.put(EXPIRATION_FIELD, getExpirationTime().toString());
		
		paymentMethod.setBillingInformation(information);
		paymentMethod.setStatus(PaymentStatus.WAITING);
		paymentMethod.setTransactionId(UUID.randomUUID().toString());
		return paymentMethod;
	}
	
	private final Instant getExpirationTime() {
		return Instant.now().plus(30L, ChronoUnit.MINUTES);
	}

	@Override
	public PaymentMethod findInfoByTransactionId(String transactionId) throws BusinessException {
		MercadoPagoPaymentMethod paymentMethod = new MercadoPagoPaymentMethod();
		paymentMethod.setTransactionId(transactionId);
		paymentMethod.setStatus(PaymentStatus.SUCCESS);
		paymentMethod.setPaymentInformation(getFakeInformation());
		return paymentMethod;
	}
	
	private Map<String, String> getFakeInformation() {
		Faker faker = Faker.instance();
		
		Map<String, String> information = new HashMap<>();
		information.put("name", faker.name().fullName());
		information.put("transactionDate", faker.date().past(10, TimeUnit.MINUTES).toInstant().toString());
		information.put("paymentType", "CREDIT_CARD");
		information.put("address", faker.address().streetAddress());
		information.put("city", faker.address().city());
		information.put("state", faker.address().state());
		information.put("country", faker.address().country());
		
		return information;
	}

}
