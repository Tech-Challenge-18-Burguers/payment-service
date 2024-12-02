package br.com.eighteenburguers.payment.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.github.javafaker.Faker;

import br.com.eighteenburguers.payment.application.service.PaymentServiceFactoryImpl;
import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.entity.Order;
import br.com.eighteenburguers.payment.core.entity.PaymentInformation;
import br.com.eighteenburguers.payment.core.entity.PaymentType;
import br.com.eighteenburguers.payment.core.exception.BusinessException;
import br.com.eighteenburguers.payment.core.repository.FinancialTransactionRepository;
import br.com.eighteenburguers.payment.core.service.PaymentServiceFactory;

@ExtendWith(MockitoExtension.class)
class CreatePaymentMethodIimplTest {

	PaymentServiceFactory factory;
	
	@Mock
	FinancialTransactionRepository repository;
	
	Faker faker;
	
	@BeforeEach
	void setup() {
		this.faker = Faker.instance();
		this.factory = new PaymentServiceFactoryImpl();
	}
	
	@Test
	void shouldBeCreatePayment() throws BusinessException {
		Order order = mockOrder();

		Answer<FinancialTransaction> answer = new Answer<FinancialTransaction>() {
			
			@Override
			public FinancialTransaction answer(InvocationOnMock invocation) throws Throwable {
				FinancialTransaction argument = invocation.getArgument(0);
				return argument;
			}
		};
		Mockito.when(repository.save(Mockito.any())).then(answer);
		
		CreatePaymentMethod usecase = new CreatePaymentMethodImpl(factory, repository);
		PaymentInformation paymentInformation = usecase.execute(order);
		
		assertNotNull(paymentInformation);
		assertEquals(order.getId(), paymentInformation.getOrderId());
	}

	private Order mockOrder() {
		Order order = new Order();
		order.setId(faker.random().nextLong());
		order.setAmount(BigDecimal.valueOf(faker.random().nextDouble()));
		order.setPaymentType(PaymentType.MERCADO_PAGO);
		order.setQuantityItems(faker.random().nextInt(1, 10));
		return order;
	}
}
