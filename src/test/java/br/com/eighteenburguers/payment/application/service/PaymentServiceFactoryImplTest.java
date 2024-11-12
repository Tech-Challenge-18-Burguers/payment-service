package br.com.eighteenburguers.payment.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eighteenburguers.payment.core.entity.PaymentType;
import br.com.eighteenburguers.payment.core.service.PaymentService;
import br.com.eighteenburguers.payment.core.service.PaymentServiceFactory;

@ExtendWith(MockitoExtension.class)
class PaymentServiceFactoryImplTest {

	@Test
	void shouldBeGetService() {
		PaymentServiceFactory factory = new PaymentServiceFactoryImpl();
		PaymentService service = factory.getService(PaymentType.MERCADO_PAGO);
		assertTrue(service instanceof MercadoPagoPaymentService);
	}
	
	@Test
	void shouldBeNotGetService() {
		PaymentServiceFactory factory = new PaymentServiceFactoryImpl();
		assertThrows(IllegalArgumentException.class, () -> factory.getService(null));
	}
}
