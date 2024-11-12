package br.com.eighteenburguers.payment.core.entity;

import java.util.Map;

public interface PaymentMethod {

	PaymentType getPaymentType();
	
	String getTransactionId();
	
	void setTransactionId(final String transactionId);
	
	Map<String, String> getBillingInformation();
	
	void setBillingInformation(Map<String, String> information);
	
	Map<String, String> getPaymentInformation();
	
	void setPaymentInformation(Map<String, String> information);
	
	PaymentStatus getStatus();
	
	void setStatus(PaymentStatus status);
}
