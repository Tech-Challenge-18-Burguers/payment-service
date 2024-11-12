package br.com.eighteenburguers.payment.core.entity;

import java.util.Map;

public class MercadoPagoPaymentMethod implements PaymentMethod {

	private String transactionId;
	private Map<String, String> billingInformation;
	private Map<String, String> paymentInformation;
	private PaymentStatus status;

	@Override
	public PaymentType getPaymentType() {
		return PaymentType.MERCADO_PAGO;
	}

	@Override
	public String getTransactionId() {
		return this.transactionId;
	}

	@Override
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public void setBillingInformation(Map<String, String> information) {
		this.billingInformation = information;
	}

	@Override
	public Map<String, String> getBillingInformation() {
		return this.billingInformation;
	}

	@Override
	public Map<String, String> getPaymentInformation() {
		return this.paymentInformation;
	}

	@Override
	public void setPaymentInformation(Map<String, String> information) {
		this.paymentInformation = information;
	}

	@Override
	public PaymentStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

}
