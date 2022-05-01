package es.uma.softcoders.eburyApp.exceptions;

public class FailedPeriodicCSVException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FailedPeriodicCSVException() {
		super();
	}
	
	public FailedPeriodicCSVException(String msg) {
		super(msg);
	}
	
}
