package es.uma.softcoders.eburyApp.exceptions;

public class EburyAppException extends Exception{
	private static final long serialVersionUID = 1L;

	public EburyAppException() {
		super();
	}
	
	public EburyAppException(String message){
		super(message);
	}
}
