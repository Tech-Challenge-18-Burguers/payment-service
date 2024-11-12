package br.com.eighteenburguers.payment.application.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.eighteenburguers.payment.core.entity.FinancialTransaction;
import br.com.eighteenburguers.payment.core.repository.FinancialTransactionRepository;

@Repository
public class FinancialTransactionRepositoryImpl implements FinancialTransactionRepository {

	@Override
	public FinancialTransaction save(FinancialTransaction transaction) {
		// TODO Implementar recurso para salvar no banco de dados
		return transaction;
	}

	@Override
	public Optional<FinancialTransaction> findById(String transactionId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
