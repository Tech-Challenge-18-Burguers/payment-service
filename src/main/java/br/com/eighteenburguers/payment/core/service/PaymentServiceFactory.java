package br.com.eighteenburguers.payment.core.service;

import br.com.eighteenburguers.payment.core.entity.PaymentType;

public interface PaymentServiceFactory {

	PaymentService getService(PaymentType type);
}
