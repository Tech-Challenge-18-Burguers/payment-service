package br.com.eighteenburguers.payment.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.entity.FinancialTransactionStatus;
import br.com.eighteenburguers.payment.core.entity.Order;
import br.com.eighteenburguers.payment.core.service.FinancialTransactionNotificationService;
import io.awspring.cloud.sqs.operations.SqsTemplate;

@ExtendWith(MockitoExtension.class)
class FinancialTransactionNotificationServiceImplTest {

	@Mock
	SqsTemplate template;

	@Test
	void shouldBeSendNotificationPaid() {
		FinancialTransaction transaction = new FinancialTransaction();
		transaction.setOrder(new Order());
		transaction.getOrder().setId(1L);
		transaction.setStatus(FinancialTransactionStatus.PAID);
		FinancialTransactionNotificationService service = new FinancialTransactionNotificationServiceImpl(template, "test");
		assertDoesNotThrow(() -> service.sendNotification(transaction));
	}
	
	@Test
	void shouldBeSendNotificationFailed() {
		FinancialTransaction transaction = new FinancialTransaction();
		transaction.setOrder(new Order());
		transaction.getOrder().setId(1L);
		transaction.setStatus(FinancialTransactionStatus.FAILED);
		FinancialTransactionNotificationService service = new FinancialTransactionNotificationServiceImpl(template, "test");
		assertDoesNotThrow(() -> service.sendNotification(transaction));
	}
}
