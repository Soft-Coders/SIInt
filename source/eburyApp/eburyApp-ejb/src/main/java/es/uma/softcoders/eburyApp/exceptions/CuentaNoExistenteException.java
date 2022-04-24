package es.uma.softcoders.eburyApp.exceptions;

public class CuentaNoExistenteException extends Exception{
	
	public CuentaNoExistenteException() {}
	
	public CuentaNoExistenteException(String msg) {
		super(msg);
	}
}
