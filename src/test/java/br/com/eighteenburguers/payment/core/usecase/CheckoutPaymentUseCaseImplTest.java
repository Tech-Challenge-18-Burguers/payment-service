package br.com.eighteenburguers.payment.core.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.entity.MercadoPagoPaymentMethod;
import br.com.eighteenburguers.payment.core.entity.Order;
import br.com.eighteenburguers.payment.core.entity.PaymentMethod;
import br.com.eighteenburguers.payment.core.entity.PaymentStatus;
import br.com.eighteenburguers.payment.core.exception.BusinessException;
import br.com.eighteenburguers.payment.core.exception.PaymentMethodInvalidException;
import br.com.eighteenburguers.payment.core.repository.FinancialTransactionRepository;
import br.com.eighteenburguers.payment.core.service.FinancialTransactionNotificationService;
import br.com.eighteenburguers.payment.core.service.PaymentService;
import br.com.eighteenburguers.payment.core.service.PaymentServiceFactory;

@ExtendWith(MockitoExtension.class)
class CheckoutPaymentUseCaseImplTest {

	@Mock
	FinancialTransactionRepository repository;

	@Mock
	PaymentServiceFactory factory;

	@Mock
	PaymentService paymentService;

	@Mock
	FinancialTransactionNotificationService notificationService;

	@Captor
	ArgumentCaptor<FinancialTransaction> captor;

	Faker faker;

	@BeforeEach
	void setup() {
		this.faker = Faker.instance();
	}

	@Test
	void shouldBeCheckout() throws BusinessException {
		String transactionId = UUID.randomUUID().toString();

		Mockito.when(repository.findById(Mockito.anyString()))
				.thenReturn(Optional.of(mockFinancialTransaction(transactionId)));
		Mockito.when(factory.getService(Mockito.any())).thenReturn(paymentService);
		Mockito.when(paymentService.findInfoByTransactionId(Mockito.anyString()))
				.thenReturn(mockPaymentMethod(transactionId));

		CheckoutPaymentUseCase usecase = new CheckoutPaymentUseCaseImpl(repository, factory, notificationService);
		usecase.execute(transactionId);

		Mockito.verify(repository, Mockito.times(1)).save(captor.capture());
		Mockito.verify(notificationService, Mockito.times(1)).sendNotification(captor.capture());
	}
	
	@Test
	void shouldBeCheckoutWithPaymentFailed() throws BusinessException {
		String transactionId = UUID.randomUUID().toString();

		PaymentMethod paymentMethod = mockPaymentMethod(transactionId);
		paymentMethod.setStatus(PaymentStatus.FAILED);
		
		Mockito.when(repository.findById(Mockito.anyString()))
				.thenReturn(Optional.of(mockFinancialTransaction(transactionId)));
		Mockito.when(factory.getService(Mockito.any())).thenReturn(paymentService);
		Mockito.when(paymentService.findInfoByTransactionId(Mockito.anyString()))
				.thenReturn(paymentMethod);

		CheckoutPaymentUseCase usecase = new CheckoutPaymentUseCaseImpl(repository, factory, notificationService);
		usecase.execute(transactionId);

		Mockito.verify(repository, Mockito.times(1)).save(captor.capture());
		Mockito.verify(notificationService, Mockito.times(1)).sendNotification(captor.capture());
	}
	
	@Test
	void shouldBeCheckoutWithPaymentInvalidStatus() throws BusinessException {
		String transactionId = UUID.randomUUID().toString();

		PaymentMethod paymentMethod = mockPaymentMethod(transactionId);
		paymentMethod.setStatus(PaymentStatus.REVERSED);
		
		Mockito.when(repository.findById(Mockito.anyString()))
				.thenReturn(Optional.of(mockFinancialTransaction(transactionId)));
		Mockito.when(factory.getService(Mockito.any())).thenReturn(paymentService);
		Mockito.when(paymentService.findInfoByTransactionId(Mockito.anyString()))
				.thenReturn(paymentMethod);

		CheckoutPaymentUseCase usecase = new CheckoutPaymentUseCaseImpl(repository, factory, notificationService);
		usecase.execute(transactionId);

		Mockito.verify(repository, Mockito.times(1)).save(captor.capture());
		Mockito.verify(notificationService, Mockito.times(1)).sendNotification(captor.capture());
	}

	@Test
	void shouldBeNotCheckoutPaymentMethodInvalid() throws BusinessException {
		String transactionId = UUID.randomUUID().toString();

		PaymentMethod invalidPaymentMethod = new MercadoPagoPaymentMethod();
		invalidPaymentMethod.setTransactionId(UUID.randomUUID().toString());
		
		Mockito.when(repository.findById(Mockito.anyString()))
				.thenReturn(Optional.of(mockFinancialTransaction(transactionId)));
		Mockito.when(factory.getService(Mockito.any())).thenReturn(paymentService);
		
		Mockito.when(paymentService.findInfoByTransactionId(Mockito.anyString()))
				.thenReturn(invalidPaymentMethod);

		CheckoutPaymentUseCase usecase = new CheckoutPaymentUseCaseImpl(repository, factory, notificationService);
		assertThrows(PaymentMethodInvalidException.class, () -> usecase.execute(transactionId));

		Mockito.verify(repository, Mockito.times(0)).save(captor.capture());
		Mockito.verify(notificationService, Mockito.times(0)).sendNotification(captor.capture());
	}

	private FinancialTransaction mockFinancialTransaction(String transactionId) {
		Order order = new Order();
		PaymentMethod payment = mockPaymentMethod(transactionId);
		FinancialTransaction transaction = new FinancialTransaction(order, payment);
		return transaction;
	}

	private PaymentMethod mockPaymentMethod(String transactionId) {
		Map<String, String> information = new HashMap<>();
		information.put("name", faker.name().fullName());
		information.put("transactionDate", faker.date().past(10, TimeUnit.MINUTES).toInstant().toString());
		information.put("paymentType", "CREDIT_CARD");
		information.put("address", faker.address().streetAddress());
		information.put("city", faker.address().city());
		information.put("state", faker.address().state());
		information.put("country", faker.address().country());

		MercadoPagoPaymentMethod paymentMethod = new MercadoPagoPaymentMethod();
		paymentMethod.setStatus(PaymentStatus.SUCCESS);
		paymentMethod.setTransactionId(transactionId);
		paymentMethod.setPaymentInformation(information);

		return paymentMethod;
	}

}
