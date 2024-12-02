package br.com.eighteenburguers.payment.core.entity;

import java.math.BigDecimal;

public class Order {

	private Long id;
	private String customerId;
	private BigDecimal amount;
	private Integer quantityItems;
	private PaymentType paymentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getQuantityItems() {
		return quantityItems;
	}

	public void setQuantityItems(Integer quantityItems) {
		this.quantityItems = quantityItems;
	}
	
	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

}
