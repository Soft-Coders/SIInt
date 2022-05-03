package es.uma.softcoders.eburyApp.exceptions;

public class CuentaExistenteException extends EburyAppException{
	
	private static final long serialVersionUID = 1L;
	
	public CuentaExistenteException(String msg) {
		super(msg);
	}
}
