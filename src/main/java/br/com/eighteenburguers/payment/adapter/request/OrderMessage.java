package br.com.eighteenburguers.payment.adapter.request;

import br.com.eighteenburguers.payment.core.entity.OrderStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderMessage {

	private Long orderId;
	private OrderStatus status;
}
