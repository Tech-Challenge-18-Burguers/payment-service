package br.com.eighteenburguers.payment.infra.configuration.context.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.eighteenburguers.payment.core.repository.FinancialTransactionRepository;
import br.com.eighteenburguers.payment.core.service.PaymentServiceFactory;
import br.com.eighteenburguers.payment.core.usecase.CreatePaymentMethod;
import br.com.eighteenburguers.payment.core.usecase.CreatePaymentMethodImpl;

@Configuration
public class PaymentUseCaseConfiguration {

	@Bean
	CreatePaymentMethod createPaymentMethod(PaymentServiceFactory factory, FinancialTransactionRepository repository) {
		return new CreatePaymentMethodImpl(factory, repository);
	}
}
