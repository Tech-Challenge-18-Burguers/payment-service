package br.com.eighteenburguers.payment.core.service;

import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;

public interface FinancialTransactionNotificationService {

	void sendNotification(FinancialTransaction notification);
}
