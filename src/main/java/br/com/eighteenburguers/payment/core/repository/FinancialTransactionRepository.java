package br.com.eighteenburguers.payment.core.repository;

import java.util.Optional;

import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;

public interface FinancialTransactionRepository {
	
	Optional<FinancialTransaction> findById(String transactionId);

	FinancialTransaction save(FinancialTransaction transaction);
}
