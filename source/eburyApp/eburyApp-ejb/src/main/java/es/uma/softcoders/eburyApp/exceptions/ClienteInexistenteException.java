package es.uma.softcoders.eburyApp.exceptions;

public class ClienteInexistenteException extends EburyAppException{
	private static final long serialVersionUID = 1L;
	
	public ClienteInexistenteException(String msg) {
		super(msg);
	}
}
