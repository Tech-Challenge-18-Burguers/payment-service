package br.com.eighteenburguers.payment.application.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.eighteenburguers.payment.adapter.FinancialTransactionMapper;
import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.entity.MercadoPagoPaymentMethod;
import br.com.eighteenburguers.payment.core.entity.Order;
import br.com.eighteenburguers.payment.core.entity.PaymentMethod;
import br.com.eighteenburguers.payment.core.entity.PaymentStatus;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

@ExtendWith(MockitoExtension.class)
class FinancialTransactionRepositoryDynamoDbTest {

	@Mock
	DynamoDbClient client;
	FinancialTransactionMapper mapper;

	Faker faker;

	@BeforeEach
	void setup() {
		this.mapper = new FinancialTransactionMapper();
		this.faker = Faker.instance();
	}

	@Test
	void shouldBeSave() {
		FinancialTransactionRepositoryDynamoDb repository = new FinancialTransactionRepositoryDynamoDb(client, mapper);
		FinancialTransaction financialTransaction = repository.save(mockFinancialTransaction());
		assertNotNull(financialTransaction);
	}

	@Test
	void shouldBeUpdate() {
		FinancialTransactionRepositoryDynamoDb repository = new FinancialTransactionRepositoryDynamoDb(client, mapper);
		FinancialTransaction transaction = mockFinancialTransaction();
		transaction.setId(UUID.randomUUID().toString());
		FinancialTransaction financialTransaction = repository.save(transaction);
		assertNotNull(financialTransaction);
	}

	@Test
	void shouldBeFindById() {
		Mockito.when(client.getItem(Mockito.any(GetItemRequest.class))).thenReturn(GetItemResponse.builder().build());
		String transactionId = UUID.randomUUID().toString();

		FinancialTransactionRepositoryDynamoDb repository = new FinancialTransactionRepositoryDynamoDb(client, mapper);
		Optional<FinancialTransaction> optional = repository.findById(transactionId);
		assertTrue(optional.isEmpty());
	}

	@Test
	void shouldBeFindById2() {
		Mockito.when(client.getItem(Mockito.any(GetItemRequest.class)))
				.thenReturn(GetItemResponse.builder().item(mockItem()).build());
		String transactionId = UUID.randomUUID().toString();

		FinancialTransactionRepositoryDynamoDb repository = new FinancialTransactionRepositoryDynamoDb(client, mapper);
		Optional<FinancialTransaction> optional = repository.findById(transactionId);
		assertTrue(optional.isPresent());
	}

	private Map<String, AttributeValue> mockItem() {
		Map<String, AttributeValue> item = new HashMap<>();

		item.put("id", AttributeValue.builder().s("txn-12345").build());
		item.put("orderId", AttributeValue.builder().n("98765").build());
		item.put("customerId", AttributeValue.builder().s("cust-67890").build());
		item.put("amount", AttributeValue.builder().n("250.50").build());
		item.put("quantityItems", AttributeValue.builder().n("3").build());
		item.put("paymentType", AttributeValue.builder().s("CREDIT_CARD").build());
		item.put("transactionId", AttributeValue.builder().s("pay-112233").build());
		item.put("paymentStatus", AttributeValue.builder().s("SUCCESS").build());
		item.put("transactionStatus", AttributeValue.builder().s("PAID").build());
		item.put("createdAt", AttributeValue.builder().s(Instant.now().toString()).build());
		item.put("updatedAt", AttributeValue.builder().s(Instant.now().toString()).build());

		return item;
	}

	private FinancialTransaction mockFinancialTransaction() {
		Order order = new Order();
		order.setId(1L);
		order.setAmount(BigDecimal.valueOf(faker.random().nextDouble()));
		order.setQuantityItems(faker.random().nextInt(1, 10));
		PaymentMethod payment = new MercadoPagoPaymentMethod();
		payment.setStatus(PaymentStatus.WAITING);
		FinancialTransaction financialTransaction = new FinancialTransaction(order, payment);
		financialTransaction.setPaidIn(Instant.now());
		return financialTransaction;
	}
}
