package br.com.eighteenburguers.payment.core.exception;

public class PaymentMethodInvalidException extends BusinessException {

	private static final long serialVersionUID = 1L;
	
	public PaymentMethodInvalidException() {
		super("PAYMETHEX001", "Payment Method Invalid Exception");
	}

}
