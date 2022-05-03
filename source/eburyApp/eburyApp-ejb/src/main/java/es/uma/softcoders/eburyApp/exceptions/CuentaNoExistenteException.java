package es.uma.softcoders.eburyApp.exceptions;

public class CuentaNoExistenteException extends EburyAppException{
	
	private static final long serialVersionUID = 1L;
	
	public CuentaNoExistenteException(String msg) {
		super(msg);
	}
}
