package br.com.eighteenburguers.payment.api.handler;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.eighteenburguers.payment.adapter.response.ErrorResponse;
import br.com.eighteenburguers.payment.adapter.response.ListErrorResponse;
import br.com.eighteenburguers.payment.core.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class HttpExceptionHandler {

	private static final String UNEXPECTED_ERROR_CODE = "ErrUnexp";
	private static final String INVALID_PROPERTY_CODE = "ErrInvProp";
	private final MessageSource messageSource;
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<?> defaultExceptionHandler(Exception e, WebRequest request) {
		log.error(e.getMessage(), e);
		ErrorResponse error = new ErrorResponse(UNEXPECTED_ERROR_CODE, e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ListErrorResponse(error));
	}
	
	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<?> businessExceptionHandler(BusinessException e, WebRequest request) {
		String code = Stream.of(e.getClass().toString().split(" ")[1].split("\\.")).skip(5)
				.collect(Collectors.joining(".")).concat(".").concat(e.getCode());
		
		final String message = getMessage(code, new ObjectError(code, e.getMessage()), request.getLocale());
		
		ErrorResponse error = new ErrorResponse(e.getCode(), message);
		return ResponseEntity.status(e.getStatusCode()).body(new ListErrorResponse(error));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<?> argumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest request) {
		ListErrorResponse response = new ListErrorResponse();
		
		for (ObjectError error : e.getAllErrors()) {
			String code = error.getCodes()[0];
			if(code != null) {
				String field = Stream.of(code.split("\\.")).skip(2).collect(Collectors.joining("."));
				String message = String.format("%s %s", this.getField(field), getMessage(error.getCodes(), error, request.getLocale()));
				ErrorResponse errorResponse = new ErrorResponse(INVALID_PROPERTY_CODE, message);
				response.addError(errorResponse);
			}
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	private String getMessage(String[] codes, ObjectError error, Locale locale) {
		for(String code: codes) {
			try {
				final String message = messageSource.getMessage(code, error.getArguments(), locale);
				return message;
			} catch (Exception e) {
				log.debug("Mensagem para o código [{}] não localizada no arquivo...", code);
			}
		}
		return error.getDefaultMessage();
	}
	
	private String getMessage(String code, ObjectError error, Locale locale) {
		try {
			return messageSource.getMessage(code, error.getArguments(), locale);
		} catch (Exception e) {
			log.debug("Mensagem para o código [{}] não localizada no arquivo...", code);
			return error.getDefaultMessage();
		}
	}
	
	private String getField(String path) {
		return path.substring(path.lastIndexOf(".")+ 1, path.length());
	}
}
