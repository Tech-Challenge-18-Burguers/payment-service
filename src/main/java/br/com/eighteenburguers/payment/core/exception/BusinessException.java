package br.com.eighteenburguers.payment.core.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;
	private String code;
    private String message;
    private Integer statusCode;
    
    protected BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
        this.statusCode = 400;
    }
    
    protected BusinessException(String code, String message, Integer statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

	public Integer getStatusCode() {
		return statusCode;
	}
	
	
    
}
