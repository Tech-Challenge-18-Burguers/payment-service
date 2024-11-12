package br.com.eighteenburguers.payment.core.service;

import java.math.BigDecimal;

import br.com.eighteenburguers.payment.core.entity.PaymentMethod;
import br.com.eighteenburguers.payment.core.exception.BusinessException;

public interface PaymentService {

	PaymentMethod create(Integer quantityItems, BigDecimal amount) throws BusinessException;
	
	PaymentMethod findInfoByTransactionId(String transactionId) throws BusinessException;
}
