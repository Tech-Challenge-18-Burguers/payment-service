package br.com.eighteenburguers.payment.core.usecase;

import br.com.eighteenburguers.payment.core.exception.BusinessException;

public interface CheckoutPaymentUseCase {

	void execute(String transactionId) throws BusinessException;
}
