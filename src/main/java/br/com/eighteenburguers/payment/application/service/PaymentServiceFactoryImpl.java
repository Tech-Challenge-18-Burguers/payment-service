package br.com.eighteenburguers.payment.application.service;

import org.springframework.stereotype.Service;

import br.com.eighteenburguers.payment.core.entity.PaymentType;
import br.com.eighteenburguers.payment.core.service.PaymentService;
import br.com.eighteenburguers.payment.core.service.PaymentServiceFactory;

@Service
public class PaymentServiceFactoryImpl implements PaymentServiceFactory {

	@Override
	public PaymentService getService(PaymentType type) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		
		switch (type) {
		case MERCADO_PAGO:
			return new MercadoPagoPaymentService();
		default:
			throw new IllegalArgumentException();
		}
	}

}
