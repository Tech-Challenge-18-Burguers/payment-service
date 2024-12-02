package br.com.eighteenburguers.payment.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eighteenburguers.payment.adapter.request.OrderRequest;
import br.com.eighteenburguers.payment.controller.PaymentController;
import br.com.eighteenburguers.payment.core.entity.PaymentInformation;
import br.com.eighteenburguers.payment.core.exception.BusinessException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")
public class PaymentResource {

	private final PaymentController controller;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody OrderRequest request) throws BusinessException {
		PaymentInformation paymentInformation = controller.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentInformation);
	}
	
	@PostMapping("/webhook/{transactionId}")
	public ResponseEntity<?> webhook(@PathVariable("transactionId") String transactionId) throws BusinessException {
		controller.checkout(transactionId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
