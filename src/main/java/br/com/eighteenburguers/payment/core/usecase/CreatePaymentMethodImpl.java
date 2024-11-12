package br.com.eighteenburguers.payment.core.usecase;

import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.entity.Order;
import br.com.eighteenburguers.payment.core.entity.PaymentInformation;
import br.com.eighteenburguers.payment.core.entity.PaymentMethod;
import br.com.eighteenburguers.payment.core.exception.BusinessException;
import br.com.eighteenburguers.payment.core.repository.FinancialTransactionRepository;
import br.com.eighteenburguers.payment.core.service.PaymentService;
import br.com.eighteenburguers.payment.core.service.PaymentServiceFactory;

public class CreatePaymentMethodImpl implements CreatePaymentMethod {

	private final PaymentServiceFactory factory;
	private final FinancialTransactionRepository repository;

	public CreatePaymentMethodImpl(PaymentServiceFactory factory, FinancialTransactionRepository repository) {
		this.factory = factory;
		this.repository = repository;
	}

	@Override
	public PaymentInformation execute(Order order) throws BusinessException {

		PaymentService service = factory.getService(order.getPaymentType());
		PaymentMethod paymentMethod = service.create(order.getQuantityItems(), order.getAmount());

		FinancialTransaction financialTransaction = repository.save(new FinancialTransaction(order, paymentMethod));
		
		PaymentInformation information = new PaymentInformation();
		information.setOrderId(financialTransaction.getOrder().getId());
		information.setAmount(financialTransaction.getOrder().getAmount());
		information.setQuantityItems(financialTransaction.getOrder().getQuantityItems());
		information.setMetadata(financialTransaction.getPaymentMethod().getBillingInformation());
		
		return information;
	}

}
