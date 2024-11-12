package br.com.eighteenburguers.payment.core.entity;

import java.math.BigDecimal;
import java.util.Map;

public class PaymentInformation {

	private Long orderId;
	private BigDecimal amount;
	private Integer quantityItems;
	private Map<String, String> metadata;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public Map<String, String> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
	
	
}
