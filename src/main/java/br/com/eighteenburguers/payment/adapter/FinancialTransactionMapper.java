package br.com.eighteenburguers.payment.adapter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.entity.FinancialTransactionStatus;
import br.com.eighteenburguers.payment.core.entity.MercadoPagoPaymentMethod;
import br.com.eighteenburguers.payment.core.entity.Order;
import br.com.eighteenburguers.payment.core.entity.PaymentMethod;
import br.com.eighteenburguers.payment.core.entity.PaymentStatus;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Component
public class FinancialTransactionMapper {

	public Map<String, AttributeValue> mapToDynamoSchema(FinancialTransaction transaction) {
		Map<String, AttributeValue> item = new HashMap<>();
		item.put("id", AttributeValue.builder().s(transaction.getId()).build());
		item.put("orderId", AttributeValue.builder().n(transaction.getOrder().getId().toString()).build());
		item.put("customerId", AttributeValue.builder().s(transaction.getOrder().getCustomerId()).build());
		item.put("amount", AttributeValue.builder().n(transaction.getOrder().getAmount().toString()).build());
		item.put("quantityItems",
				AttributeValue.builder().n(transaction.getOrder().getQuantityItems().toString()).build());
		item.put("paymentType",
				AttributeValue.builder().s(transaction.getPaymentMethod().getPaymentType().toString()).build());
		item.put("transactionId",
				AttributeValue.builder().s(transaction.getPaymentMethod().getTransactionId()).build());
		item.put("paymentStatus",
				AttributeValue.builder().s(transaction.getPaymentMethod().getStatus().toString()).build());
		item.put("paymentMethod",
				AttributeValue.builder().s(transaction.getPaymentMethod().getPaymentType().toString()).build());
		item.put("transactionStatus", AttributeValue.builder().s(transaction.getStatus().toString()).build());
		item.put("createdAt", AttributeValue.builder().s(transaction.getCreatedAt().toString()).build());
		item.put("updatedAt", AttributeValue.builder().s(transaction.getUpdatedAt().toString()).build());
		return item;
	}
	
	public FinancialTransaction fromDynamoDbItem(Map<String, AttributeValue> item) {
        FinancialTransaction transaction = new FinancialTransaction();

        transaction.setId(item.get("id").s());
        transaction.setCreatedAt(Instant.parse(item.get("createdAt").s()));
        transaction.setUpdatedAt(Instant.parse(item.get("updatedAt").s()));

        transaction.setStatus(FinancialTransactionStatus.valueOf(item.get("transactionStatus").s()));

        PaymentMethod paymentMethod = new MercadoPagoPaymentMethod();
        paymentMethod.setTransactionId(item.get("transactionId").s());
        paymentMethod.setStatus(PaymentStatus.valueOf(item.get("paymentStatus").s()));
        transaction.setPaymentMethod(paymentMethod);

        Order order = new Order();
        order.setId(Long.parseLong(item.get("orderId").n()));
        order.setCustomerId(item.get("customerId").s());
        order.setAmount(BigDecimal.valueOf(Double.parseDouble(item.get("amount").n())));
        order.setQuantityItems(Integer.parseInt(item.get("quantityItems").n()));
        transaction.setOrder(order);

        return transaction;
    }
}
