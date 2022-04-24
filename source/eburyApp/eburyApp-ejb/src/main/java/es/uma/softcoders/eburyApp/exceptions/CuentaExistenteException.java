package es.uma.softcoders.eburyApp.exceptions;

public class CuentaExistenteException extends Exception{
	
	public CuentaExistenteException() {}
	
	public CuentaExistenteException(String msg) {
		super(msg);
	}
}
