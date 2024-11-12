package br.com.eighteenburguers.payment.controller;

import org.springframework.stereotype.Component;

import br.com.eighteenburguers.payment.adapter.OrderMapper;
import br.com.eighteenburguers.payment.adapter.request.OrderRequest;
import br.com.eighteenburguers.payment.core.entity.PaymentInformation;
import br.com.eighteenburguers.payment.core.exception.BusinessException;
import br.com.eighteenburguers.payment.core.usecase.CreatePaymentMethod;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PaymentController {

	private final CreatePaymentMethod createPaymentMethod;
	private final OrderMapper orderMapper;
	
	public PaymentInformation create(OrderRequest request) throws BusinessException {
		return createPaymentMethod.execute(orderMapper.toEntity(request));
	}
}
