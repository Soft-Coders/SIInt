package es.uma.softcoders.eburyApp.exceptions;

public class ContrasenaIncorrectaException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ContrasenaIncorrectaException(String msg) {
		super(msg);
	}
}
