package br.com.eighteenburguers.payment.adapter.request;

import java.math.BigDecimal;

import br.com.eighteenburguers.payment.core.entity.PaymentType;
import lombok.Data;

@Data
public class OrderRequest {

	private Long orderId;
	private String customerId;
	private BigDecimal amount;
	private Integer quantityItems;
	private PaymentType paymentType;
}
