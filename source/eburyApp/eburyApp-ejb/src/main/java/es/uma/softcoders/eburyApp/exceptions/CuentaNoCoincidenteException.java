package es.uma.softcoders.eburyApp.exceptions;

public class CuentaNoCoincidenteException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public CuentaNoCoincidenteException(String message) {
		super(message);
	}

}
