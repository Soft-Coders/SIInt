package es.uma.softcoders.eburyApp.exceptions;

public class CuentaNoExistenteException extends EburyAppException{
	
	public CuentaNoExistenteException() {}
	
	public CuentaNoExistenteException(String msg) {
		super(msg);
	}
}
