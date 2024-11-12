package br.com.eighteenburguers.payment.core.usecase;

import br.com.eighteenburguers.payment.core.entity.Order;
import br.com.eighteenburguers.payment.core.entity.PaymentInformation;
import br.com.eighteenburguers.payment.core.exception.BusinessException;

public interface CreatePaymentMethod {

	PaymentInformation execute(Order order) throws BusinessException;
}
