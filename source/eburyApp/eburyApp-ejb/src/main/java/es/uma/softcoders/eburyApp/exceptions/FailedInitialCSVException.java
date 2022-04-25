package es.uma.softcoders.eburyApp.exceptions;

public class FailedInitialCSVException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FailedInitialCSVException() {
		super();
	}
	
	public FailedInitialCSVException(String msg) {
		super(msg);
	}
	
}
