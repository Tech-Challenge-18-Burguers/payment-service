package br.com.eighteenburguers.payment.adapter.response;

import java.util.HashSet;
import java.util.Set;

import br.com.eighteenburguers.payment.core.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListErrorResponse {

	private Set<ErrorResponse> errors;

	public ListErrorResponse() {
		this.errors = new HashSet<>();
	}
	
	public ListErrorResponse(ErrorResponse error) {
		this.errors = new HashSet<>();
		this.errors.add(error);
	}
	
	public ListErrorResponse(ErrorResponse ...errors) {
		this.errors = new HashSet<>();
		this.errors.addAll(Set.of(errors));
	}
	
	public void addError(ErrorResponse error) {
		this.errors.add(error);
	}

	public ListErrorResponse(BusinessException e) {
		this.errors = Set.of(new ErrorResponse(e.getCode(), e.getMessage()));
	}
}
