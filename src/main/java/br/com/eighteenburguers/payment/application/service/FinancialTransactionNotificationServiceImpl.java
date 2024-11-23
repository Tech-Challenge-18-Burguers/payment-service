package br.com.eighteenburguers.payment.application.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.eighteenburguers.payment.adapter.request.OrderMessage;
import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.entity.FinancialTransactionStatus;
import br.com.eighteenburguers.payment.core.entity.OrderStatus;
import br.com.eighteenburguers.payment.core.service.FinancialTransactionNotificationService;
import br.com.eighteenburguers.payment.infra.queue.QueueSqsMessageSender;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FinancialTransactionNotificationServiceImpl extends QueueSqsMessageSender<OrderMessage> implements FinancialTransactionNotificationService  {

	public FinancialTransactionNotificationServiceImpl(SqsTemplate template, @Value("${sqs.queue.sender}") String queue) {
		super(template, queue);
	}

	@Override
	public void sendNotification(FinancialTransaction notification) {
		log.debug("notification: {}", notification);
		OrderMessage message = new OrderMessage();
		message.setOrderId(notification.getOrder().getId());
		if(notification.getStatus().equals(FinancialTransactionStatus.PAID)) {
			message.setStatus(OrderStatus.PAID);
		} else {
			message.setStatus(OrderStatus.CANCELED);
		}
		
		this.sendMessage(message, "ORDER_PAID");
	}

}
