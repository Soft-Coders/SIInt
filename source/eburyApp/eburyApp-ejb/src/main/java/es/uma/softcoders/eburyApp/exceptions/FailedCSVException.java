package es.uma.softcoders.eburyApp.exceptions;

public class FailedCSVException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FailedCSVException() {
		super();
	}
	
	public FailedCSVException(String msg) {
		super(msg);
	}
	
}
