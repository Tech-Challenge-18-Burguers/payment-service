package br.com.eighteenburguers.payment.core.exception;

public class FinancialTransactionNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public FinancialTransactionNotFoundException() {
		super("FINTREX001", "Financial Transaction Not Found");
	}
}
