package br.com.eighteenburguers.payment.core.usecase;

import java.time.Instant;

import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.entity.FinancialTransactionStatus;
import br.com.eighteenburguers.payment.core.entity.PaymentMethod;
import br.com.eighteenburguers.payment.core.entity.PaymentStatus;
import br.com.eighteenburguers.payment.core.exception.BusinessException;
import br.com.eighteenburguers.payment.core.exception.FinancialTransactionNotFoundException;
import br.com.eighteenburguers.payment.core.exception.PaymentMethodInvalidException;
import br.com.eighteenburguers.payment.core.repository.FinancialTransactionRepository;
import br.com.eighteenburguers.payment.core.service.FinancialTransactionNotificationService;
import br.com.eighteenburguers.payment.core.service.PaymentService;
import br.com.eighteenburguers.payment.core.service.PaymentServiceFactory;

public class CheckoutPaymentUseCaseImpl implements CheckoutPaymentUseCase {

	private final FinancialTransactionRepository repository;
	private final PaymentServiceFactory factory;
	private final FinancialTransactionNotificationService notificationService;

	public CheckoutPaymentUseCaseImpl(FinancialTransactionRepository repository, PaymentServiceFactory factory,
			FinancialTransactionNotificationService notificationService) {
		this.repository = repository;
		this.factory = factory;
		this.notificationService = notificationService;
	}

	@Override
	public void execute(String transactionId) throws BusinessException {
		FinancialTransaction financialTransaction = repository.findById(transactionId)
				.orElseThrow(FinancialTransactionNotFoundException::new);

		PaymentService service = factory.getService(financialTransaction.getPaymentMethod().getPaymentType());
		PaymentMethod paymentMethod = service.findInfoByTransactionId(financialTransaction.getPaymentMethod().getTransactionId());
		
		validate(financialTransaction, paymentMethod);

		mappingPaymentMethodToFinancialTransaction(paymentMethod, financialTransaction);

		repository.save(financialTransaction);

		notificationService.sendNotification(financialTransaction);
	}

	private void validate(FinancialTransaction financialTransaction, PaymentMethod paymentMethod)
			throws PaymentMethodInvalidException {
		if(!financialTransaction.getPaymentMethod().getTransactionId().equals(paymentMethod.getTransactionId())) {
			throw new PaymentMethodInvalidException();
		}
	}

	private void mappingPaymentMethodToFinancialTransaction(PaymentMethod payment, FinancialTransaction transaction) {
		transaction.getPaymentMethod().setPaymentInformation(payment.getPaymentInformation());
		transaction.setStatus(mappingStatus(payment.getStatus()));
		transaction.setPaidIn(Instant.now());
	}

	private FinancialTransactionStatus mappingStatus(PaymentStatus status) {
		switch (status) {
		case SUCCESS:
			return FinancialTransactionStatus.PAID;
		case FAILED:
			return FinancialTransactionStatus.FAILED;
		default:
			return FinancialTransactionStatus.REVERSED;
		}
	}

}
