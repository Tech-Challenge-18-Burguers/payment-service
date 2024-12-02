package br.com.eighteenburguers.payment.application.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.com.eighteenburguers.payment.adapter.FinancialTransactionMapper;
import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.repository.FinancialTransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

@Slf4j
@Repository
@AllArgsConstructor
public class FinancialTransactionRepositoryDynamoDb implements FinancialTransactionRepository {

	public static final String TABLE_NAME = "payment_db";
	private final DynamoDbClient client;
	private final FinancialTransactionMapper mapper;

	@Override
	public FinancialTransaction save(FinancialTransaction transaction) {
		if(transaction.getId() == null) {
			transaction.setId(UUID.randomUUID().toString());
			return this.create(transaction);
		} else {
			return this.update(transaction);
		}
	}

	@Override
	public Optional<FinancialTransaction> findById(String transactionId) {
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("id", AttributeValue.builder().s(transactionId).build());
		GetItemRequest request = GetItemRequest.builder().key(key).tableName(TABLE_NAME).build();
		GetItemResponse response = client.getItem(request);
		if(!response.hasItem()) {
			return Optional.empty();
		}
		
		FinancialTransaction transaction = mapper.fromDynamoDbItem(response.item());
		return Optional.of(transaction);
	}
	
	private FinancialTransaction create(FinancialTransaction transaction) {
		Map<String, AttributeValue> item = mapper.mapToDynamoSchema(transaction);
		PutItemRequest request = PutItemRequest.builder().tableName(TABLE_NAME).item(item).build();
		PutItemResponse response = client.putItem(request);
		log.info("Item saved with successfully: {}", response);
		return transaction;
	}
	
	private FinancialTransaction update(FinancialTransaction transaction) {
		Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(transaction.getId()).build());
        
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":orderId", AttributeValue.builder().n(transaction.getOrder().getId().toString()).build());
        expressionAttributeValues.put(":customerId", AttributeValue.builder().s(transaction.getOrder().getCustomerId()).build());
        expressionAttributeValues.put(":amount", AttributeValue.builder().n(transaction.getOrder().getAmount().toString()).build());
        expressionAttributeValues.put(":quantityItems", AttributeValue.builder().n(transaction.getOrder().getQuantityItems().toString()).build());
        expressionAttributeValues.put(":paymentType", AttributeValue.builder().s(transaction.getPaymentMethod().getPaymentType().toString()).build());
        expressionAttributeValues.put(":transactionId", AttributeValue.builder().s(transaction.getPaymentMethod().getTransactionId()).build());
        expressionAttributeValues.put(":paymentStatus", AttributeValue.builder().s(transaction.getPaymentMethod().getStatus().toString()).build());
        expressionAttributeValues.put(":transactionStatus", AttributeValue.builder().s(transaction.getStatus().toString()).build());
        expressionAttributeValues.put(":createdAt", AttributeValue.builder().s(transaction.getCreatedAt().toString()).build());
        expressionAttributeValues.put(":updatedAt", AttributeValue.builder().s(transaction.getUpdatedAt().toString()).build());
        expressionAttributeValues.put(":paidIn", AttributeValue.builder().s(transaction.getPaidIn().toString()).build());
		
        String updateExpression = "SET orderId = :orderId, customerId = :customerId, amount = :amount, " +
                "quantityItems = :quantityItems, paymentType = :paymentType, " +
                "transactionId = :transactionId, paymentStatus = :paymentStatus, " +
                "transactionStatus = :transactionStatus, createdAt = :createdAt, " +
                "updatedAt = :updatedAt, paidIn = :paidIn";
        
        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .updateExpression(updateExpression)
                .expressionAttributeValues(expressionAttributeValues)
                .build();
        
        UpdateItemResponse response = this.client.updateItem(updateItemRequest);
        
        log.info("Update successfully: {}", response);
        
        return transaction;
	}

}
