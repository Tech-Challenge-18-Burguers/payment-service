package br.com.eighteenburguers.payment.core.entity;

import java.time.Instant;

public class FinancialTransaction {

	private String id;
	private Order order;
	private PaymentMethod paymentMethod;
	private FinancialTransactionStatus status;
	private Instant paidIn;
	private Instant createdAt;
	private Instant updatedAt;

	public FinancialTransaction(Order order, PaymentMethod paymentMethod) {
		super();
		this.order = order;
		this.paymentMethod = paymentMethod;
	}

	public Order getOrder() {
		return order;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public FinancialTransactionStatus getStatus() {
		return status;
	}

	public Instant getPaidIn() {
		return paidIn;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setStatus(FinancialTransactionStatus status) {
		this.status = status;
	}

	public void setPaidIn(Instant paidIn) {
		this.paidIn = paidIn;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
