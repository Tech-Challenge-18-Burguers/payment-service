package br.com.eighteenburguers.payment.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eighteenburguers.payment.core.exception.BusinessException;
import br.com.eighteenburguers.payment.core.service.PaymentService;

@ExtendWith(MockitoExtension.class)
class MercadoPagoPaymentServiceTest {

	@Test
	void shouldBeFindByTransactionId() throws BusinessException {
		PaymentService service = new MercadoPagoPaymentService();
		assertNotNull(service.findInfoByTransactionId(UUID.randomUUID().toString()));
	}
}
