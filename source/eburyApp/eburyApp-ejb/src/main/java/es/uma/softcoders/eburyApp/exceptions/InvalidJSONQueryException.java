package es.uma.softcoders.eburyApp.exceptions;

public class InvalidJSONQueryException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidJSONQueryException() {
		super();
	}
	
	public InvalidJSONQueryException(String msg) {
		super(msg);
	}
}
