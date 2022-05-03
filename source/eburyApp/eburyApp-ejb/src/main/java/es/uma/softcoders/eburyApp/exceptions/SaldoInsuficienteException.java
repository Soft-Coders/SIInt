package es.uma.softcoders.eburyApp.exceptions;

public class SaldoInsuficienteException extends EburyAppException{
	
	private static final long serialVersionUID = 1L;
	
	public SaldoInsuficienteException(String msg) {
		super(msg);
	}
}
